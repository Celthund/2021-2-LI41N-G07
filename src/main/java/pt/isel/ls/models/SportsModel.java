package pt.isel.ls.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            throw new ServerErrorException("Failed getting sport with id = " + sid + ".");
        }
        return sport;
    }

    public LinkedList<Sport> getAllSports(String skip, String top) throws ServerErrorException {
        LinkedList<Sport> sports = new LinkedList<>();
        PGSimpleDataSource db = getDataSource();
        try {
            Connection connection = db.getConnection();
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
            connection.close();
        } catch (SQLException throwable) {
            throw new ServerErrorException("Failed getting all sports.");
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
            throw new ServerErrorException("Failed creating sport.");
        }
        return sport;
    }
}
