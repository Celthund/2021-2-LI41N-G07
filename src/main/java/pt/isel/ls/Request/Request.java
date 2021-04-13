package pt.isel.ls.Request;

import java.util.HashMap;
import java.util.LinkedList;

public class Request {
    private String path;
    Method method;
    final private HashMap<String, LinkedList<String>> queryString = new HashMap<>();
    final private HashMap<String, String> parameters = new HashMap<>();

    public Request(Method method, String path, String queryString){
        setMethod(method);
        setPath(path);
        setQueryString(queryString);
    }

    public Request(Method method, String path){
        setMethod(method);
        setPath(path);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (method != null) result.append(method).append(" ");
        if (path != null) result.append(path).append(" ");
        if (parameters.size() > 0 ) result.append(" parameters: ").append(parameters.toString());
        if (queryString.size() > 0) result.append(" queryString: ").append(queryString.toString());
        return result.toString();
    }

    public Method getMethod() {
        return method;
    }

    public HashMap<String, LinkedList<String>> getQueryString() {
        return queryString;
    }

    public String getPath() {
        return path;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setQueryString(String queryString) {
        String[] params = queryString.split("&");

        for (String param : params) {
            String[] keyValue = param.split("=");
            if (this.queryString.containsKey(keyValue[0])) {
                this.queryString.get(keyValue[0]).add(keyValue[1].replace("+", " "));
            } else {
                LinkedList<String> l = new LinkedList<>();
                l.add(keyValue[1].replace("+", " "));
                this.queryString.put(keyValue[0], l);
            }
        }
    }

    public void setPath(String path) {
        this.path = path.toLowerCase();
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }
    public void addParameter(String key, String value){
        parameters.put(key, value);
    }
}
