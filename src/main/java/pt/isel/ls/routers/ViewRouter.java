package pt.isel.ls.routers;

import java.lang.reflect.Type;
import java.util.HashMap;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.exceptions.ViewAlreadyExistsException;
import pt.isel.ls.exceptions.ViewNotFoundException;
import pt.isel.ls.views.View;

public class ViewRouter {

    HashMap<String, View> views = new HashMap<>();

    public void addView(Type requestResult, String accept, View view) throws AppException {
        String key = requestResult.getTypeName() + "|" + accept;
        if (views.containsKey(key)) {
            throw new ViewAlreadyExistsException("View Already exists.");
        }
        views.put(key, view);
    }

    public View findView(Type requestResult, String accept) throws AppException {
        String key = requestResult.getTypeName() + "|" + accept;
        if (views.containsKey(key)) {
            return views.get(key);
        }
        throw new ViewNotFoundException("View not found.");
    }

}
