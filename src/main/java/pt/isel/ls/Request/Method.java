package pt.isel.ls.Request;

import pt.isel.ls.Exceptions.InvalidRequestException;

public enum Method {
    GET, POST, DELETE, OPTION;

    public static Method getMethod(String method) throws InvalidRequestException {
        switch (method.toUpperCase()){
            case "GET": return GET;
            case "POST": return POST;
            case "DELETE": return DELETE;
            case "OPTION": return OPTION;
            default: throw new InvalidRequestException("Invalid Method");
        }
    }
}
