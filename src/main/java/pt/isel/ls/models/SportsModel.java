package pt.isel.ls.models;

import java.sql.*;
import java.util.LinkedList;
import org.postgresql.ds.PGSimpleDataSource;
import pt.isel.ls.exceptions.ServerErrorException;
import pt.isel.ls.models.domainclasses.Sport;
import static pt.isel.ls.utils.Utils.getDataSource;

public class SportsModel {

    public Sport getSportById(String sid) throws ServerErrorException {
        Sport sport = null;
        // Get the configurations to set up the DB connection
        PGSimpleDataSource db = getDataSource();
        try {
            Connection connection = db.getConnection();
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
            connection.close();
        } catch (SQLException throwable) {
            throw new ServerErrorException("Server Error! Failed get Sport.");
        }
        return sport;
    }

    public LinkedList<Sport> getAllSports() throws ServerErrorException {
        LinkedList<Sport> sports = new LinkedList<>();
        PGSimpleDataSource db = getDataSource();
        try {
            Connection connection = db.getConnection();
            Statement statement = connection.createStatement();
            ResultSet sportResult = statement.executeQuery("SELECT * FROM sports");
            while (sportResult.next()) {
                sports.add(new Sport(
                    sportResult.getInt("sid"),
                    sportResult.getString("name"),
                    sportResult.getString("description")));
            }
            connection.close();
        } catch (SQLException throwable) {
            throw new ServerErrorException("Server Error! Failed getting all the Sports.");
        }
        return sports;
    }

    public Sport createSport(String name, String description) throws ServerErrorException {
        Sport sport = null;
        PGSimpleDataSource db = getDataSource();
        Connection connection = null;
        try {
            connection = db.getConnection();
            connection.setAutoCommit(false);
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
                connection.commit();
            }

            preparedStatement.close();
            connection.setAutoCommit(true);

        } catch (SQLException throwable) {
            throw new ServerErrorException("Server Error! Failed to create Sport.");
        }
        return sport;
    }
}
