package pt.isel.ls.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import org.postgresql.ds.PGSimpleDataSource;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.exceptions.ServerErrorException;
import pt.isel.ls.models.domainclasses.Sport;
import static pt.isel.ls.utils.DataSource.getDataSource;

public class SportsModel {

    public Sport getSportById(String sid, Connection connection) throws ServerErrorException {
        Sport sport = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM sports WHERE sid = ?");
            preparedStatement.setInt(1, Integer.parseInt(sid));

            ResultSet sportResult = preparedStatement.executeQuery();
            if (sportResult.next()) {
                sport = new Sport(
                    sportResult.getInt("sid"),
                    sportResult.getString("name"),
                    sportResult.getString("description"));
            }
            preparedStatement.close();
        } catch (SQLException throwable) {
            throw new ServerErrorException("Failed getting sport with id = " + sid + ".");
        }
        return sport;
    }

    public LinkedList<Sport> getAllSports(String skip, String top, Connection connection) throws ServerErrorException {
        LinkedList<Sport> sports = new LinkedList<>();
        try {
            PreparedStatement preparedStatement;
            StringBuilder sqlCmd = new StringBuilder("SELECT * FROM sports");

            //Paging implementation
            if (skip != null && top != null) {
                sqlCmd.append(" LIMIT ? OFFSET ?;");
                preparedStatement = connection.prepareStatement(sqlCmd.toString());
                preparedStatement.setInt(1, Integer.parseInt(top));
                preparedStatement.setInt(2, Integer.parseInt(skip));

            } else {
                sqlCmd.append(";");
                preparedStatement = connection.prepareStatement(sqlCmd.toString());
            }

            ResultSet sportResult = preparedStatement.executeQuery();

            while (sportResult.next()) {
                sports.add(new Sport(
                    sportResult.getInt("sid"),
                    sportResult.getString("name"),
                    sportResult.getString("description")));
            }
        } catch (SQLException throwable) {
            throw new ServerErrorException("Failed getting all sports.");
        }
        return sports;
    }

    public Sport createSport(String name, String description, Connection connection) throws ServerErrorException {
        Sport sport = null;
        try {
            String sqlCmd = "INSERT INTO sports(name, description) VALUES (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlCmd);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            if (preparedStatement.executeUpdate() == 1) {
                sqlCmd = "SELECT * FROM sports ORDER BY sid DESC LIMIT 1;";
                ResultSet sportResult = connection.createStatement().executeQuery(sqlCmd);
                if (sportResult.next()) {
                    sport = new Sport(
                        sportResult.getInt("sid"),
                        sportResult.getString("name"),
                        sportResult.getString("description"));
                }
            }
            preparedStatement.close();
        } catch (SQLException throwable) {
            throw new ServerErrorException("Failed creating sport.");
        }
        return sport;
    }

    public LinkedList<Sport> getSportsByRid(String rid, Connection connection) throws AppException {
        LinkedList<Sport> sports = null;
        try {
            PreparedStatement preparedStatement;

            StringBuilder sqlCmd = new StringBuilder("SELECT DISTINCT * FROM sports "
                + "INNER JOIN activities "
                + "ON sports.sid = activities.sid "
                + "INNER JOIN routes "
                + "ON activities.rid = routes.rid "
                + "WHERE activities.ts_deleted is NULL AND activities.rid = ? ");

            preparedStatement = connection.prepareStatement(sqlCmd.toString());

            preparedStatement.setInt(1, Integer.parseInt(rid));

            ResultSet sportResult = preparedStatement.executeQuery();
            // Creates the activity with value it got from the query

            sports = createSportList(sportResult);

            preparedStatement.close();
        } catch (SQLException throwable) {
            throw new ServerErrorException("Failed getting sports by route id.");
        }
        return sports;
    }

    // Creates alink list with all the activities got from alink query
    private LinkedList<Sport> createSportList(ResultSet sportResult)
            throws SQLException {
        LinkedList<Sport> sports = new LinkedList<>();
        Sport sport = null;

        // For each row it will get the columns values, add it to an Activity "holder" and then
        //add it to the list to be returns by this method
        while (sportResult.next()) {
            // The constructor for the Activity value holder
            sport = new Sport(
                sportResult.getInt("sid"),
                sportResult.getString("name"),
                sportResult.getString("description"));
            sports.add(sport);
        }
        return sports;
    }
}
