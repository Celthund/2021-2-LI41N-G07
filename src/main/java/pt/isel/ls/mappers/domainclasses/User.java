package pt.isel.ls.mappers.domainclasses;

import java.util.LinkedList;

public class User {
    public final String name;
    public final String email;
    public final int id;
    public LinkedList<Activity> activities = null;

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

    public void setActivities(LinkedList<Activity> activities) {
        this.activities = activities;
    }
}
