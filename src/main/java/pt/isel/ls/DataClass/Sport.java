package pt.isel.ls.DataClass;

public class Sport {

    public final int sid;
    public final String name, description;

    public Sport(int id, String name, String description) {
        this.sid = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Sport {" +
                "sid=" + sid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
