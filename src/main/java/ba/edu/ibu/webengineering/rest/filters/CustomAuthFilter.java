package ba.edu.ibu.webengineering.rest.filters;


import ba.edu.ibu.webengineering.core.api.auth.AuthorizationApi;
import ba.edu.ibu.webengineering.core.exceptions.auth.UnauthorizedException;
import ba.edu.ibu.webengineering.core.model.User;
import ba.edu.ibu.webengineering.rest.annotations.PermissionGroup;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static ba.edu.ibu.webengineering.common.constants.ErrorMessages.USER_DOES_NOT_HAVE_PERMISSION_TO_ACCESS_THIS_RESOURCE;

public class CustomAuthFilter implements Filter {
    public static final String JWT_TOKEN = "x-jwt-token";
    public static final String AUTH_FILTER_USER = "AUTH_FILTER_USER";
    private final Map<PermissionGroup, List<FilterRoute>> permissionGroupRoutes;
    private final AuthorizationApi authorizationApi;

    public CustomAuthFilter(Map<PermissionGroup, List<FilterRoute>> permissionGroupRoutes, AuthorizationApi authorizationApi) {
        this.permissionGroupRoutes = permissionGroupRoutes;
        this.authorizationApi = authorizationApi;
    }

    public static String removeLastChar(String s) {
        return (s == null || s.length() == 0) ? null : (s.substring(0, s.length() - 1));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        /*
          *** IMPORTANT ***
          Do not remove or comment out this line. This is the backbone of our authorization procedure.
         */
        request.removeAttribute(AUTH_FILTER_USER);

        try {
            HttpServletRequest req = (HttpServletRequest) request;
            String uri = preProcessURI(req.getRequestURI());
            RequestMethod method = RequestMethod.valueOf(req.getMethod());

            HttpServletResponse res = (HttpServletResponse) response;

            if (method.toString().equals(RequestMethod.OPTIONS.toString())) {
                res.setHeader("Access-Control-Allow-Origin", "*");
                res.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
                res.setHeader("Access-Control-Max-Age", "3600");
                res.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With, x-jwt-token");
                res.setStatus(HttpStatus.NO_CONTENT.value());
            } else {
                String token = req.getHeader(JWT_TOKEN);
                User user = !StringUtils.isBlank(token) ? authorizationApi.decodeUser(token) : null;

                if (user == null || user.getType() == null) {
                    // Check if trying to access non public api
                    AtomicBoolean isPublicRoute = new AtomicBoolean(permissionGroupRoutes.get(PermissionGroup.PUBLIC).stream().anyMatch(route -> route.getPattern().matcher(uri.toLowerCase()).find() && route.getRequestMethod().equals(method)));

                    permissionGroupRoutes.get(PermissionGroup.PUBLIC).forEach(s -> System.out.println(s.getPattern()));

                    if (!isPublicRoute.get()) {
                        // TODO: this should be in configuration
                        List.of("/swagger", "/favicon.ico", "/v3/api-docs", "/webjars", "/stripe", "/twilio").forEach(whitelisted -> {
                            if (uri.startsWith(whitelisted)) {
                                isPublicRoute.set(true);
                            }
                        });
                    }

                    if (!isPublicRoute.get()) {
                        throw new UnauthorizedException(USER_DOES_NOT_HAVE_PERMISSION_TO_ACCESS_THIS_RESOURCE);
                    }
                }
                request.setAttribute(AUTH_FILTER_USER, user);

                filterChain.doFilter(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            HttpServletResponse res = (HttpServletResponse) response;
            HttpServletRequest req = (HttpServletRequest) request;
            if (e.getClass().toString().contains("UnauthorizedException")) {
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized exception! You do not have access to requested resource! This will be reported to the administrator");
            } else {
                res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ooops! Something went wrong. Please contact system administrator! " + e.getMessage());
            }
        }
    }

    private String preProcessURI(String uri) {
        if (uri.endsWith("/")) {
            uri = removeLastChar(uri);
        }
        return uri;
    }
}
