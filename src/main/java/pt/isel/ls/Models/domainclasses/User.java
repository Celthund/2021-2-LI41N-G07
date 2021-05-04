package pt.isel.ls.Models.domainclasses.Models.domainclasses;

public class User {
    public final String name;
    public final String email;
    public final int id;

    public User(String name, String email, int id) {
        this.name = name;
        this.email = email;
        this.id = id;
    }

    @Override
    public String toString() {
        return "User {"
            + "name='" + name + '\''
            + ", email='" + email + '\''
            + ", id=" + id
            + '}';
    }
}
