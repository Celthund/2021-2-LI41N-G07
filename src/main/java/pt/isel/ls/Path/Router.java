package pt.isel.ls.Path;
import pt.isel.ls.Commands.RequestHandler;
import pt.isel.ls.Handler;
import pt.isel.ls.Request;

import java.util.Optional;

public class Router {
    Handler handler = new Handler();

    public void addRoute(String method, String path,  RequestHandler handler) {
        Handler handlerMethod = new Handler(), handlerFound = null;
        handlerMethod.setId(method.toUpperCase());

        for (Handler h: this.handler.handlers){
            if(h.equals(handlerMethod)) {
                handlerFound = h;
                break;
            }
        }

        if(handlerFound == null){
            handlerFound = handlerMethod;
            this.handler.handlers.add(handlerFound);
        }

        String[] allPaths = path.split("/");

        boolean flag = false;
        Handler tmp;
        for(int i = 1; i < allPaths.length; i++){
            tmp = new Handler();
            tmp.setId(allPaths[i].toLowerCase());

            for (Handler current: handlerFound.handlers) {
                if(current.equals(tmp)){
                    handlerFound = current;
                    flag = true;
                    break;
                }
            }
            if(!flag){
                handlerFound.handlers.add(tmp);
                handlerFound = tmp;
            } else {
                flag = false;
            }
        }
        handlerFound.setCallFunction(handler);
    }

    public Optional<RequestHandler> findRoute(Request request) { return null; }
}
