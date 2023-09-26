package ba.edu.ibu.webengineering.rest.filters;

import org.springframework.web.bind.annotation.RequestMethod;

import java.util.regex.Pattern;

public class FilterRoute {
    private final Pattern pattern;
    private RequestMethod requestMethod;
    private String path;

    public FilterRoute(RequestMethod requestMethod, String path) {
        this.requestMethod = requestMethod;
        this.path = path;
        this.pattern = Pattern.compile(path.replaceAll("\\{[a-z]+}", "[0-9]*"));
    }

    @Override
    public String toString() {
        return "FilterRoute{" +
                "requestMethod=" + requestMethod +
                ", path='" + path + '\'' +
                '}';
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Pattern getPattern() {
        return pattern;
    }
}

