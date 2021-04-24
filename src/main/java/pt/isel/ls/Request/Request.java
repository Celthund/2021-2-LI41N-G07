package pt.isel.ls.Request;

import pt.isel.ls.Exceptions.InvalidRequestException;

import java.util.HashMap;
import java.util.LinkedList;

public class Request {
    private String[] path;
    private Method method;
    // Hashmap where we store all parameters
    final private HashMap<String, LinkedList<String>> queryString = new HashMap<>();
    // Hashmap to store the variable path value
    final private HashMap<String, String> parameters = new HashMap<>();

    // Constructor if there are parameters to receive
    public Request(Method method, String path, String queryString) throws InvalidRequestException {
        setMethod(method);
        setPath(path);
        setQueryString(queryString);
    }

    // Constructor if its just the method and path
    public Request(Method method, String path) {
        setMethod(method);
        setPath(path);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (method != null) result.append(method).append(" ");
        if (path != null) {
            result.append("/");
            for (String s : path) {
                result.append(s).append("/");
            }
            result.append(" ");
        }
        if (parameters.size() > 0) result.append(" parameters: ").append(parameters);
        if (queryString.size() > 0) result.append(" queryString: ").append(queryString);
        return result.toString();
    }

    // Setters and getters
    public void setMethod(Method method) {
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

    public void setPath(String path) {
        this.path = path.toLowerCase().substring(1).split("/");
    }

    public String[] getPath() {
        return path;
    }


    // Adds to the queryString Hashmap all the parameters
    public void setQueryString(String queryString) throws InvalidRequestException {
        // To separate all the parameters
        String[] params = queryString.split("&");

        for (String param : params) {
            // Separates the name of the parameters with is value (row, row value)
            String[] keyValue = param.split("=");
            // It needs to be length too because we have just a "row" and a "value" corresponding to that row
            if (keyValue.length != 2)
                throw new InvalidRequestException("Query String with wrong format");
            // We opted to store multiple parameters that are equal (multiple names, emails, etc)
            if (this.queryString.containsKey(keyValue[0])) {
                this.queryString.get(keyValue[0]).add(keyValue[1].replace("+", " "));
            } else {
                // If the parameter doesnt yet exist we create a linkedlist to store the value and add that parameter to the hashmap
                LinkedList<String> l = new LinkedList<>();
                l.add(keyValue[1].replace("+", " "));
                this.queryString.put(keyValue[0], l);
            }
        }
    }

    public HashMap<String, LinkedList<String>> getQueryString() {
        return queryString;
    }


    public void addParameter(String key, String value) {
        parameters.put(key, value);
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }
}
