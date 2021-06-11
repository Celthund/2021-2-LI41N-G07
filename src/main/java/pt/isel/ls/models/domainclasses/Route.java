package pt.isel.ls.models.domainclasses;

import java.util.LinkedList;

public class Route {

    public final int rid;
    public final int distance;
    public final String startLocation;
    public final String endLocation;
    public LinkedList<Sport> sports = null;

    public Route(int rid, int distance, String startLocation, String endLocation) {
        this.rid = rid;
        this.distance = distance;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
    }

    @Override
    public String toString() {
        return "Route {"
            + "rid=" + rid
            + ", distance=" + distance
            + ", startLocation='" + startLocation + '\''
            + ", endLocation='" + endLocation + '\''
            + '}';
    }

    public void setSports(LinkedList<Sport> sports) {
        this.sports = sports;
    }
}
