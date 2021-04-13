package pt.isel.ls.Request;

public enum Method {
    GET, POST;

    public static Method getMethod(String method){
        switch (method.toUpperCase()){
            case "GET": return GET;
            case "POST": return POST;
            default: return null;
        }
    }
}
