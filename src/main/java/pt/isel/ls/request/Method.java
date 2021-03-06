package pt.isel.ls.request;

import pt.isel.ls.exceptions.InvalidRequestException;

public enum Method {
    GET, POST, DELETE, OPTION, LISTEN;

    public static Method getMethod(String method) throws InvalidRequestException {
        switch (method.toUpperCase()) {
            case "GET":
                return GET;
            case "POST":
                return POST;
            case "DELETE":
                return DELETE;
            case "OPTION":
                return OPTION;
            case "LISTEN":
                return LISTEN;
            default:
                throw new InvalidRequestException("Invalid Method");
        }
    }
}
