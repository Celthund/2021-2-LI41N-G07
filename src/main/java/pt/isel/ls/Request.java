package pt.isel.ls;

import java.util.HashMap;
import java.util.LinkedList;

public class Request {
    private String method, path;
    final private HashMap<String, LinkedList<String>> parameters = new HashMap<>();

    public Request(String method, String path, String parameters){
        setMethod(method);
        setPath(path);
        setParameters(parameters);
    }

    public Request(String method, String path){
        setMethod(method);
        setPath(path);
    }

    @Override
    public String toString() {
        return method + " " + path + " parameters: " + parameters.toString();
    }

    public String getMethod() {
        return method;
    }

    public HashMap<String, LinkedList<String>> getParameters() {
        return parameters;
    }

    public String getPath() {
        return path;
    }

    public void setMethod(String method) {
        this.method = method.toUpperCase();
    }

    public void setParameters(String parameters) {
        String[] params = parameters.split("&");

        for (String param : params) {
            String[] keyValue = param.split("=");
            if (this.parameters.containsKey(keyValue[0])) {
                this.parameters.get(keyValue[0]).add(keyValue[1].replace("+", " "));
            } else {
                LinkedList<String> l = new LinkedList<>();
                l.add(keyValue[1].replace("+", " "));
                this.parameters.put(keyValue[0], l);
            }

        }
    }

    public void setPath(String path) {
        this.path = path.toLowerCase();
    }
}
