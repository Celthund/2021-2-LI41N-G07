package pt.isel.ls.DataClass;

public class Sport {

    public final int id;
    public final String name, description;

    public Sport(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Sport {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
