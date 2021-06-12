package pt.isel.ls.views;

import java.util.HashMap;
import java.util.LinkedList;

public class PageNavigation {

    public static int getSkip(HashMap<String, LinkedList<String>> queryString) {
        int skip = 0;
        if (queryString.containsKey("skip")) {
            skip = Integer.parseInt(queryString.get("skip").getFirst());
        }
        return skip;
    }

    public static int getTop(HashMap<String, LinkedList<String>> queryString) {
        int top = 5;
        if (queryString.containsKey("top")) {
            top = Integer.parseInt(queryString.get("top").getFirst());
        }
        return top;
    }
}
