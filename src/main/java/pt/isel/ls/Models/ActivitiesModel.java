package pt.isel.ls.Models;

import org.postgresql.ds.PGSimpleDataSource;
import pt.isel.ls.DataClass.Activity;
import pt.isel.ls.Exceptions.BadRequestException;
import java.sql.*;
import java.util.LinkedList;

import static pt.isel.ls.Utils.Utils.getDataSource;

public class ActivitiesModel {

    final private SportsModel sports =  new SportsModel();
    private final  UserModel users = new UserModel();
    final private RoutesModel routes = new RoutesModel();

    public LinkedList<Activity> getActivitiesByTops(String sid, String orderBy, String date, String rid) throws BadRequestException {
        Activity activity = null;
        LinkedList<Activity> activites = new LinkedList<>();


        StringBuilder sqlCmd = new StringBuilder("SELECT * FROM activities WHERE sid = ?");
        if(date != null) sqlCmd.append(" and date = ?");
        if(rid != null) sqlCmd.append(" and rid = ?");

        if((orderBy.equals("ascending"))) {
            sqlCmd.append(" order by duration asc;");
        }else if(orderBy.equals("descending")){
            sqlCmd.append(" order by duration desc;");
        }else{
            throw new BadRequestException("Invalid Order By");
        }

        // Get the configurations to set up the DB connection
        PGSimpleDataSource db = getDataSource();
        try {
            Connection connection = db.getConnection();
            int i = 1;
            PreparedStatement preparedStatement = connection.prepareStatement(sqlCmd.toString());
            preparedStatement.setInt(i++, Integer.parseInt(sid));
            if(date != null) preparedStatement.setDate(i++, Activity.dateToDate(date));
            if(rid != null) preparedStatement.setInt(i, Integer.parseInt(rid));

            ResultSet activityResult = preparedStatement.executeQuery();
            int tmpRid;
            while (activityResult.next()) {
                activity = new Activity(
                        activityResult.getInt("aid"),
                        users.getUserById(Integer.toString(activityResult.getInt("uid")) ),
                        sports.getSportById(Integer.toString(activityResult.getInt("sid"))),
                        (tmpRid = activityResult.getInt("rid")) == 0 ? null : routes.getRouteById(Integer.toString(tmpRid)),
                        activityResult.getDate("date"),
                        activityResult.getLong("duration"));
                activites.add(activity);
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return activites;
    }

    public Activity getActivityByUid(String uid) {
        Activity activity = null;

        // Get the configurations to set up the DB connection
        PGSimpleDataSource db = getDataSource();
        try {
            Connection connection = db.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM activities WHERE uid = ?");
            preparedStatement.setInt(1, Integer.parseInt(uid));

            ResultSet activityResult = preparedStatement.executeQuery();
            if (activityResult.next()) {
                int rid;
                activity = new Activity(
                        activityResult.getInt("aid"),
                        users.getUserById(uid),
                        sports.getSportById(Integer.toString(activityResult.getInt("sid"))),
                        (rid = activityResult.getInt("rid")) == 0 ? null : routes.getRouteById(Integer.toString(rid)),
                        activityResult.getDate("date"),
                        activityResult.getLong("duration"));
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return activity;
    }

    public Activity postActivity(String sid, String uid, String duration, String date, String rid) throws BadRequestException {
        Date sqlDate = Activity.dateToDate(date);
        if (sqlDate == null) {
            throw new BadRequestException("Invalid date format.(yyyy-mm-dd)");
        }
        long sqlDuration = Activity.durationToLong(duration);
        if (sqlDuration == -1) {
            throw new BadRequestException("Invalid duration format. (hh:mm:ss.fff)");
        }
        Activity activity = null;
        PGSimpleDataSource db = getDataSource();
        Connection connection = null;
        try {
            connection = db.getConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement;
            String sqlCmd;
            if(rid == null){
                sqlCmd = "INSERT INTO activities(uid, sid, date, duration) VALUES (?, ?, ?, ?);";
                preparedStatement = connection.prepareStatement(sqlCmd);
                preparedStatement.setInt(1, Integer.parseInt(uid));
                preparedStatement.setInt(2, Integer.parseInt(sid));
                preparedStatement.setDate(3, sqlDate);
                preparedStatement.setLong(4, sqlDuration);
            }

            else {
                sqlCmd = "INSERT INTO activities(uid, rid, sid, date, duration) VALUES (?, ?, ?, ?, ?);";
                preparedStatement = connection.prepareStatement(sqlCmd);
                preparedStatement.setInt(1, Integer.parseInt(uid));
                preparedStatement.setInt(2,Integer.parseInt(rid));
                preparedStatement.setInt(3, Integer.parseInt(sid));
                preparedStatement.setDate(4, sqlDate);
                preparedStatement.setLong(5, sqlDuration);
            }

            if (preparedStatement.executeUpdate() == 1) {
                sqlCmd = "SELECT * FROM activities ORDER BY aid DESC LIMIT 1;";
                ResultSet activityResult = connection.createStatement().executeQuery(sqlCmd);
                if (activityResult.next()) {
                    int checkRid;
                    activity = new Activity(
                            activityResult.getInt("aid"),
                            users.getUserById(uid),
                            sports.getSportById(sid),
                            (checkRid = activityResult.getInt("rid")) == 0 ? null : routes.getRouteById(Integer.toString(checkRid)),
                            activityResult.getDate("date"),
                            activityResult.getLong("duration"));
                }

                connection.commit();
            }

            preparedStatement.close();
            connection.setAutoCommit(true);
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activity;
    }

    public Activity getActivityByAidSid(String aid, String sid) {
        Activity activity = null;

        // Get the configurations to set up the DB connection
        PGSimpleDataSource db = getDataSource();
        try {
            Connection connection = db.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM activities WHERE aid = ? AND sid = ?");
            preparedStatement.setInt(1, Integer.parseInt(aid));
            preparedStatement.setInt(2, Integer.parseInt(sid));

            ResultSet activityResult = preparedStatement.executeQuery();
            if (activityResult.next()) {
                int rid;
                activity = new Activity(
                        Integer.parseInt(aid),
                        users.getUserById(Integer.toString(activityResult.getInt("uid"))),
                        sports.getSportById(sid),
                        (rid = activityResult.getInt("rid")) == 0 ? null : routes.getRouteById(Integer.toString(rid)),
                        activityResult.getDate("date"),
                        activityResult.getLong("duration"));
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return activity;
    }

    public Activity getActivityBySid(String sid) {
        Activity activity = null;

        // Get the configurations to set up the DB connection
        PGSimpleDataSource db = getDataSource();
        try {
            Connection connection = db.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM activities WHERE sid = ?");
            preparedStatement.setInt(1, Integer.parseInt(sid));

            ResultSet activityResult = preparedStatement.executeQuery();
            if (activityResult.next()) {
                int rid;
                activity = new Activity(
                        activityResult.getInt("aid"),
                        users.getUserById(Integer.toString(activityResult.getInt("uid"))),
                        sports.getSportById(sid),
                        (rid = activityResult.getInt("rid")) == 0 ? null : routes.getRouteById(Integer.toString(rid)),
                        activityResult.getDate("date"),
                        activityResult.getLong("duration"));
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return activity;
    }
}
