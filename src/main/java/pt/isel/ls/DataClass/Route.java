package pt.isel.ls.DataClass;

public class Route {

    public final int id, distance;
    public final String startLocation, endLocation;

    public Route(int id, int distance, String startLocation, String endLocation) {
        this.id = id;
        this.distance = distance;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
    }

    @Override
    public String toString() {
        return "Route {" +
                "id=" + id +
                ", distance=" + distance +
                ", startLocation='" + startLocation + '\'' +
                ", endLocation='" + endLocation + '\'' +
                '}';
    }
}
