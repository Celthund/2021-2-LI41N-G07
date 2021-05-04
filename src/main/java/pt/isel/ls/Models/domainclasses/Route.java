package pt.isel.ls.Models.domainclasses.Models.domainclasses;

public class Route {

    public final int rid;
    public final int distance;
    public final String startLocation;
    public final String endLocation;

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
}
