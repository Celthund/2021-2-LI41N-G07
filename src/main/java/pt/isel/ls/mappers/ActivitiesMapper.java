package pt.isel.ls.mappers;

import java.sql.*;
import java.util.LinkedList;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.exceptions.BadRequestException;
import pt.isel.ls.exceptions.ServerErrorException;
import pt.isel.ls.mappers.domainclasses.Activity;


public class ActivitiesMapper {

    // Variable used in the creation of Activity
    private final SportsMapper sports = new SportsMapper();
    private final UserMapper users = new UserMapper();
    private final RoutesMapper routes = new RoutesMapper();

    public LinkedList<Activity> getActivitiesByTops(String sid, String orderBy, String date,
                                                    String rid, String distance, String skip, String top,
                                                    Connection connection)
            throws AppException {

        // Stores all the activities get from the query
        LinkedList<Activity> activities;

        // Will concatenate the parts of the query
        StringBuilder sqlCmd = new StringBuilder("SELECT * FROM activities");

        if (distance == null) {
            sqlCmd.append(" WHERE ts_deleted IS NULL AND sid = ?");
        } else {
            sqlCmd.append(" INNER JOIN ROUTES on activities.rid = routes.rid WHERE "
                + "ts_deleted IS NULL AND sid = ? and distance > ?");
        }
        // If they are null it means the user didn't search for them so we don't concatenate
        if (date != null) {
            sqlCmd.append(" and date = ?");
        }
        if (rid != null) {
            sqlCmd.append(" and rid = ?");
        }

        // Checks what order the user pretends to see the query
        if ((orderBy.equals("ascending"))) {
            sqlCmd.append(" order by duration asc");
        } else if (orderBy.equals("descending")) {
            sqlCmd.append(" order by duration desc");
        } else {
            throw new BadRequestException("Invalid Order By");
        }


        if (skip != null && top != null) {
            sqlCmd.append(" LIMIT ? OFFSET ?");
        }

        try {
            int i = 1;
            PreparedStatement preparedStatement = connection.prepareStatement(sqlCmd.toString());
            // Sets the sport the user wants to search for
            preparedStatement.setInt(i++, Integer.parseInt(sid));

            if (distance != null) {
                preparedStatement.setInt(i++, Integer.parseInt(distance));
            }
            // If they are null it means the user didn't search for them so we don't set the value
            if (date != null) {
                preparedStatement.setDate(i++, Activity.dateToDate(date));
            }
            if (rid != null) {
                preparedStatement.setInt(i++, Integer.parseInt(rid));
            }
            if (skip != null && top != null) {
                preparedStatement.setInt(i++, Integer.parseInt(top));
                preparedStatement.setInt(i, Integer.parseInt(skip));
            }
            // Executes the query
            ResultSet activityResult = preparedStatement.executeQuery();

            // Value to get the route value (in case its not null)
            activities = createActivityList(activityResult, connection);

            preparedStatement.close();
        } catch (SQLException  | NumberFormatException throwables) {
            throw new ServerErrorException("Failed getting activities by tops.");
        }
        return activities;
    }

    public LinkedList<Activity> getActivitiesByUid(String uid, String skip, String top,
                                                   Connection connection) throws AppException {
        LinkedList<Activity> activities;

        try {
            PreparedStatement preparedStatement;
            StringBuilder sqlCmd = new StringBuilder("SELECT * FROM activities WHERE ts_deleted IS NULL AND uid = ?");

            if (skip != null && top != null) {
                sqlCmd.append(" LIMIT ? OFFSET ?");
                preparedStatement = connection.prepareStatement(sqlCmd.toString());
                preparedStatement.setInt(2, Integer.parseInt(top));
                preparedStatement.setInt(3, Integer.parseInt(skip));
            } else {
                preparedStatement = connection.prepareStatement(sqlCmd.toString());
            }

            preparedStatement.setInt(1, Integer.parseInt(uid));

            ResultSet activityResult = preparedStatement.executeQuery();
            // Value to get the route value (in case its not null)

            activities = createActivityList(activityResult, connection);

            preparedStatement.close();
        } catch (SQLException | NumberFormatException throwables) {
            throw new ServerErrorException("Failed getting activities by uid.");
        }
        return activities;
    }

    public LinkedList<Activity> getActivitiesByRid(String rid, String skip, String top,
                                                   Connection connection) throws AppException {
        LinkedList<Activity> activities;

        try {
            PreparedStatement preparedStatement;

            StringBuilder sqlCmd = new StringBuilder("SELECT * FROM activities WHERE ts_deleted IS NULL AND rid = ?");

            if (skip != null && top != null) {
                sqlCmd.append(" LIMIT ? OFFSET ?");
                preparedStatement = connection.prepareStatement(sqlCmd.toString());
                preparedStatement.setInt(2, Integer.parseInt(top));
                preparedStatement.setInt(3, Integer.parseInt(skip));
            } else {
                preparedStatement = connection.prepareStatement(sqlCmd.toString());
            }

            preparedStatement.setInt(1, Integer.parseInt(rid));

            ResultSet activityResult = preparedStatement.executeQuery();
            // Value to get the route value (in case its not null)

            activities = createActivityList(activityResult, connection);
            preparedStatement.close();
        } catch (SQLException | NumberFormatException throwables) {
            throw new ServerErrorException("Failed getting activities by rid.");
        }
        return activities;
    }

    // Server that creates Activity
    public Activity createActivity(String sid, String uid, String duration, String date, String rid,
                                   Connection connection)
            throws AppException {
        // Transforms the String date to Date date
        Date sqlDate = Activity.dateToDate(date);

        // Invalid data, throw request
        if (sqlDate == null) {
            throw new BadRequestException("Invalid date format.(yyyy-mm-dd)");
        }
        long sqlDuration = Activity.durationToLong(duration);
        if (sqlDuration == -1) {
            throw new BadRequestException("Invalid duration format. (hh:mm:ss.fff)");
        }
        Activity activity = null;
        try {
            PreparedStatement preparedStatement;
            //  Will store the Sql command
            String sqlCmd;

            // If rid is null it means the user didn't introduce it so the query will not have it
            if (rid == null || rid.length() == 0) {
                sqlCmd = "INSERT INTO activities(uid, sid, date, duration) VALUES (?, ?, ?, ?);";
                preparedStatement = connection.prepareStatement(sqlCmd);
                preparedStatement.setInt(2, Integer.parseInt(sid));
                preparedStatement.setDate(3, sqlDate);
                preparedStatement.setLong(4, sqlDuration);
            } else {
                sqlCmd = "INSERT INTO activities(uid, rid, sid, date, duration) VALUES (?, ?, ?, ?, ?);";
                preparedStatement = connection.prepareStatement(sqlCmd);
                preparedStatement.setInt(2, Integer.parseInt(rid));
                preparedStatement.setInt(3, Integer.parseInt(sid));
                preparedStatement.setDate(4, sqlDate);
                preparedStatement.setLong(5, sqlDuration);
            }

            preparedStatement.setInt(1, Integer.parseInt(uid));

            // Creates alink new activity with the value it got from the query
            if (preparedStatement.executeUpdate() == 1) {
                sqlCmd = "SELECT * FROM activities WHERE ts_deleted IS NULL ORDER BY aid DESC LIMIT 1;";
                ResultSet activityResult = connection.createStatement().executeQuery(sqlCmd);
                if (activityResult.next()) {
                    int checkRid;
                    activity = new Activity(
                        activityResult.getInt("aid"),
                        // Creates alink user with the user got from the query with the value it got from the query
                        users.getUserById(uid, connection),
                        // Creates alink user with the value got from the query with the sid sent by the user
                        sports.getSportById(sid, connection),
                        // Checks if the user pretend to get Route, if its null, just put the Route in
                        // activity to null if not it will query for the rid sent by the user and store in activity
                        (checkRid = activityResult.getInt("rid")) == 0
                            ? null : routes.getRouteById(Integer.toString(checkRid), connection),
                        activityResult.getDate("date"),
                        activityResult.getLong("duration"));
                }
            }
            preparedStatement.close();
        } catch (SQLException  | NumberFormatException throwables) {
            throw new ServerErrorException("Failed creating activity.");
        }
        return activity;
    }

    public Activity getActivityByAidSid(String aid, String sid, Connection connection) throws AppException {
        Activity activity = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM activities WHERE ts_deleted IS NULL AND aid = ? AND sid = ?");
            preparedStatement.setInt(1, Integer.parseInt(aid));
            preparedStatement.setInt(2, Integer.parseInt(sid));

            ResultSet activityResult = preparedStatement.executeQuery();
            // Creates the activity with value it got from the query
            if (activityResult.next()) {
                int rid;
                activity = new Activity(
                    Integer.parseInt(aid),
                    // Creates alink user with the user got from the query with the value it got from the query
                    users.getUserById(Integer.toString(activityResult.getInt("uid")), connection),
                    sports.getSportById(sid, connection),
                    // Checks if the user pretend to get Route, if its null, just put the Route in
                    // activity to null if not it will query for the rid sent by the user and store in activity
                    (rid = activityResult.getInt("rid")) == 0 ? null :
                        routes.getRouteById(Integer.toString(rid), connection),
                    activityResult.getDate("date"),
                    activityResult.getLong("duration"));
            }
            preparedStatement.close();
        } catch (SQLException | NumberFormatException throwables) {
            throw new ServerErrorException("Failed getting activity by aid and sid.");
        }
        return activity;
    }

    public LinkedList<Activity> getActivitiesBySid(String sid, String skip, String top,
                                                   Connection connection) throws AppException {
        LinkedList<Activity> activities = null;
        try {
            PreparedStatement preparedStatement;

            StringBuilder sqlCmd = new StringBuilder("SELECT * FROM activities WHERE ts_deleted is NULL AND sid = ? ");

            //Paging implementation
            if (skip != null && top != null) {
                sqlCmd.append(" LIMIT ? OFFSET ?");
                preparedStatement = connection.prepareStatement(sqlCmd.toString());
                preparedStatement.setInt(2, Integer.parseInt(top));
                preparedStatement.setInt(3, Integer.parseInt(skip));
            } else {
                preparedStatement = connection.prepareStatement(sqlCmd.toString());
            }

            preparedStatement.setInt(1, Integer.parseInt(sid));

            ResultSet activityResult = preparedStatement.executeQuery();
            // Creates the activity with value it got from the query

            activities = createActivityList(activityResult, connection);
            preparedStatement.close();
        } catch (SQLException | NumberFormatException throwables) {
            throw new ServerErrorException("Failed getting activity by sid.");
        }
        return activities;
    }

    // Method that deletes the requested activity by not showing in any more querys
    public LinkedList<Activity> deleteActivity(String uid, LinkedList<String> aid,
                                               Connection connection) throws AppException {

        // Holder for the activities
        LinkedList<Activity> activities = new LinkedList<>();

        // It means the user didnt specified any activity so it just returns an empty list
        if (aid.size() == 0) {
            return new LinkedList<>();
        }

        // Get the needed paramaters to be used by the prepareStatement to delete the rows
        StringBuilder numberOfAid = new StringBuilder().append("( ?");
        numberOfAid.append(", ?".repeat(aid.size() - 1));
        numberOfAid.append(")");
        try {

            // Prepares the query by concatenating the fixed part (the part where it checks for the null ts_deleted)
            //to the number of the aids the user sent
            PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM activities WHERE ts_deleted IS NULL AND uid = ? AND aid IN "
                    + numberOfAid.toString());

            preparedStatement.setInt(1, Integer.parseInt(uid));
            int i = 2;
            for (String id : aid) {
                preparedStatement.setInt(i++, Integer.parseInt(id));
            }

            // Stores the queryResult
            ResultSet activityResult = preparedStatement.executeQuery();

            // Creates the activity with value it got from the query
            activities = createActivityList(activityResult, connection);
            preparedStatement.close();

            // Query that will set all those activity values to the current time
            //effectively removing from user access
            preparedStatement = connection.prepareStatement("UPDATE activities SET ts_deleted = ? "
                + "WHERE ts_deleted IS NULL AND uid = ? AND aid IN " + numberOfAid.toString());
            preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(2, Integer.parseInt(uid));
            i = 3;
            for (String id : aid) {
                preparedStatement.setInt(i++, Integer.parseInt(id));
            }
        } catch (SQLException | NumberFormatException throwables) {
            throw new ServerErrorException("Failed deleting activity.");
        }
        // Returns all the activity removed
        return activities;
    }

    // Creates alink list with all the activities got from alink query
    private LinkedList<Activity> createActivityList(ResultSet activityResult, Connection connection)
            throws SQLException, ServerErrorException {
        LinkedList<Activity> activities = new LinkedList<>();
        Activity activity = null;

        int tmpRid;
        // For each row it will get the columns values, add it to an Activity "holder" and then
        //add it to the list to be returns by this method
        while (activityResult.next()) {
            // The constructor for the Activity value holder
            activity = new Activity(
                activityResult.getInt("aid"),
                users.getUserById(Integer.toString(activityResult.getInt("uid")), connection),
                sports.getSportById(Integer.toString(activityResult.getInt("sid")), connection),
                (tmpRid = activityResult.getInt("rid")) == 0
                    ? null : routes.getRouteById(Integer.toString(tmpRid), connection),
                activityResult.getDate("date"),
                activityResult.getLong("duration"));
            activities.add(activity);
        }

        return activities;
    }
}
