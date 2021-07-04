package pt.isel.ls.request;

import pt.isel.ls.exceptions.InvalidRequestException;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;

public class Request {
    // Hashmap where we store all parameters
    private final HashMap<String, LinkedList<String>> queryStrings = new HashMap<>();
    // Hashmap where the requested headers are stored
    private final HashMap<String, LinkedList<String>> headers = new HashMap<>();
    // Hashmap to store the variable path value
    private final HashMap<String, String> parameters = new HashMap<>();
    private String[] path;
    private Method method;

    // Constructor if there are parameters to receive
    public Request(Method method, String path, String queryString) throws InvalidRequestException {
        setMethod(method);
        setPath(path);
        setQueryStrings(queryString);
    }

    // Constructor if its just the method and path
    public Request(Method method, String path) {
        setMethod(method);
        setPath(path);
    }

    public Request(String request) throws InvalidRequestException {
        parse(request);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (method != null) {
            result.append(method).append(" ");
        }
        if (path != null) {
            result.append("/");
            for (String s : path) {
                result.append(s).append("/");
            }
            result.append(" ");
        }
        if (parameters.size() > 0) {
            result.append(" parameters: ").append(parameters);
        }
        if (queryStrings.size() > 0) {
            result.append(" queryString: ").append(queryStrings);
        }
        return result.toString();
    }

    public Method getMethod() {
        return method;
    }

    // Setters and getters
    public void setMethod(Method method) {
        this.method = method;
    }

    public String[] getPath() {
        return path;
    }

    public void setPath(String path) {
        String[] allPath = path.toLowerCase().substring(1).split("/");
        if (allPath.length == 1 && allPath[0].length() == 0) {
            this.path = new String[0];
        } else {
            this.path = allPath;
        }

    }

    public HashMap<String, LinkedList<String>> getQueryStrings() {
        return queryStrings;
    }

    // Adds to the queryString Hashmap all the parameters
    public void setQueryStrings(String queryStrings) throws InvalidRequestException {
        // To separate all the parameters
        String[] params = queryStrings.split("&");

        for (String param : params) {
            // Separates the name of the parameters with is value (row, row value)
            String[] keyValue = param.split("=", 2);
            // It needs to be length too because we have just alink "row" and alink "value" corresponding to that row
            if (keyValue.length != 2) {
                throw new InvalidRequestException("Query String with wrong format");
            }
            // We opted to store multiple parameters that are equal (multiple names, emails, etc)
            if (this.queryStrings.containsKey(keyValue[0])) {
                this.queryStrings.get(keyValue[0]).add(URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8));
            } else {
                // If the parameter doesnt yet exist we create alink linked list
                // to store the value and add that parameter to the hashmap
                LinkedList<String> l = new LinkedList<>();
                l.add(URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8));
                this.queryStrings.put(keyValue[0], l);
            }
        }
    }

    public void addParameter(String key, String value) {
        parameters.put(key, value);
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }

    private void setHeader(String headerString) throws InvalidRequestException {
        // To separate all the headers
        String[] headers = headerString.split("\\|");

        for (String header : headers) {
            // Separates the name of the header with is value (row, row value)
            String[] keyValue = header.split(":", 2);
            // It needs to be length too because we have just alink "row" and alink "value" corresponding to that row
            if (keyValue.length != 2) {
                throw new InvalidRequestException("Query String with wrong format");
            }
            // We opted to store multiple headers that are equal
            if (this.headers.containsKey(keyValue[0])) {
                //Maintain replacement case the filename has separation needs
                this.headers.get(keyValue[0]).add(URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8));
            } else {
                // If the header doesn't yet exist we create alink linklist to store the value and
                // add that header to the hashmap
                LinkedList<String> list = new LinkedList<>();
                list.add(URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8));
                this.headers.put(keyValue[0], list);
            }
        }
    }

    public HashMap<String, LinkedList<String>> getHeaders() {
        return headers;
    }

    private void parse(String request) throws InvalidRequestException {
        String[] arr = request.split(" ");

        if (arr.length < 2) { //doesn't have path, method or both
            throw new InvalidRequestException();
        }

        if (arr.length == 2) { // doesn't have parameters
            setMethod(Method.getMethod(arr[0]));
            setPath(arr[1]);
        } else if (arr.length == 4) {
            setMethod(Method.getMethod(arr[0]));
            setPath(arr[1]);
            setHeader(arr[2]);
            setQueryStrings(arr[3]);
        } else if (arr.length == 3) {
            setMethod(Method.getMethod(arr[0]));
            setPath(arr[1]);
            int idxOfParameters = arr[2].indexOf("=");
            int idxOfHeader = arr[2].indexOf(":");

            if (idxOfHeader > 0) {
                setHeader(arr[2]);
            } else if (idxOfParameters > 0) {
                setQueryStrings(arr[2]);
            }
        }
    }
}
