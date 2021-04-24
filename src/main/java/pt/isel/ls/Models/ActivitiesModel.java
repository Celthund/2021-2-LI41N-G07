package pt.isel.ls.Models;

import org.postgresql.ds.PGSimpleDataSource;
import pt.isel.ls.DataClass.Activity;
import pt.isel.ls.DataClass.Route;
import pt.isel.ls.DataClass.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import static pt.isel.ls.Utils.Utils.getDataSource;

public class ActivitiesModel {

    public LinkedList<Activity> getActivitiesByTops(String sid, String orderBy, String date, String rid) {
        return null;
    }

    public Activity getActivityByUid(String uid) {
        Activity activity = null;
        User user = new UserModel().getUserById(uid);

        // Get the configurations to set up the DB connection
        PGSimpleDataSource db = getDataSource();
        try {
            Connection connection = db.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM activity WHERE uid = ?");
            preparedStatement.setInt(1, Integer.parseInt(uid));

            ResultSet routeResult = preparedStatement.executeQuery();
            if (routeResult.next()) {
                activity = new Activity(
                        routeResult.getInt("aid"),
                        routeResult.getLong("duration"),
                        user, null, null);
                        //routeResult.getInt("sid"),

                       // routeResult.getString("endLocation"));
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return activity;
    }

    public Activity postActivity(String s, String sid, String uid, String duration, String date) {
        return null;
    }

    public Activity getActivityByAid_Sid(String aid, String sid) {
        return null;
    }

    public Activity getActivityBySid(String sid) {
        return null;
    }
}
