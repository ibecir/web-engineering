package ba.edu.ibu.webengineering.rest.configuration;

import ba.edu.ibu.webengineering.core.api.auth.AuthorizationApi;
import ba.edu.ibu.webengineering.core.exceptions.GeneralException;
import ba.edu.ibu.webengineering.rest.annotations.ApiPermission;
import ba.edu.ibu.webengineering.rest.annotations.PermissionGroup;
import ba.edu.ibu.webengineering.rest.filters.CustomAuthFilter;
import ba.edu.ibu.webengineering.rest.filters.FilterRoute;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ba.edu.ibu.webengineering.common.utils.ClassUtils.getClasses;

@Configuration
public class FilterConfiguration {
    public static final String SLASH = "/";
    public static final String DOUBLE_SLASH = "//";

    @Bean
    public FilterRegistrationBean<CustomAuthFilter> authFilter(AuthorizationApi authorizationApi) {
        final FilterRegistrationBean<CustomAuthFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new CustomAuthFilter(getPathsWithAnnotation(), authorizationApi));
        registrationBean.setOrder(5);

        return registrationBean;
    }

    private String concatPaths(String controller, String api) {
        String path = controller + SLASH + api;
        path = path.replace(DOUBLE_SLASH, SLASH);
        if (!path.startsWith(SLASH)) {
            path = SLASH + path;
        }
        if (path.endsWith(SLASH)) {
            path = path.substring(0, path.length() - 1);
        }

        return path.toLowerCase();
    }

    private Map<PermissionGroup, List<FilterRoute>> getPathsWithAnnotation() {
        final Map<PermissionGroup, List<FilterRoute>> groupPaths = Arrays.stream(PermissionGroup.values()).collect(Collectors.toMap(k -> k, v -> new ArrayList<>()));
        List<Class<?>> classes;

        try {
            classes = getClasses("com.doctors365.rest.controllers");
        } catch (Exception e) {
            throw new GeneralException(e);
        }

        for (Class<?> aClass : classes) {
            String controllerPath = SLASH;
            if (aClass.isAnnotationPresent(RequestMapping.class) && aClass.getAnnotation(RequestMapping.class).value().length > 0) {
                controllerPath = aClass.getAnnotation(RequestMapping.class).value()[0];
            }

            for (Method method : List.of(aClass.getMethods())) {
                if (method.isAnnotationPresent(ApiPermission.class) && method.isAnnotationPresent(RequestMapping.class)) {
                    String apiPath = SLASH;
                    if (method.getAnnotation(RequestMapping.class).path().length > 0) {
                        apiPath = method.getAnnotation(RequestMapping.class).path()[0];
                    }
                    if (method.getAnnotation(RequestMapping.class).method().length > 0) {
                        for (PermissionGroup permissionGroup : method.getAnnotation(ApiPermission.class).value()) {
                            groupPaths.get(permissionGroup).add(new FilterRoute(method.getAnnotation(RequestMapping.class).method()[0], concatPaths(controllerPath, apiPath)));
                        }
                    }
                }
            }
        }

        return groupPaths;
    }
}

