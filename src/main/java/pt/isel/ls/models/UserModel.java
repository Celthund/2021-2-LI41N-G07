package pt.isel.ls.models;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import org.postgresql.ds.PGSimpleDataSource;
import pt.isel.ls.exceptions.ServerErrorException;
import pt.isel.ls.models.domainclasses.User;
import static pt.isel.ls.utils.Utils.getDataSource;

// Handles the data sent by the corresponding view
public class UserModel {

    public User getUserById(String id) throws ServerErrorException {
        User user = null;
        // Get the configurations to set up the DB connection
        PGSimpleDataSource db = getDataSource();
        try {
            Connection connection = db.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE uid = ?");
            preparedStatement.setInt(1, Integer.parseInt(id));

            ResultSet userResult = preparedStatement.executeQuery();
            if (userResult.next()) {
                user = new User(
                    userResult.getString("name"),
                    userResult.getString("email"),
                    userResult.getInt("uid"));
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwable) {
            throw new ServerErrorException("Failed getting user with id = " + id + ".");
        }
        return user;
    }

    public LinkedList<User> getAllUsers(String skip, String top) throws ServerErrorException {
        LinkedList<User> users = new LinkedList<>();
        PGSimpleDataSource db = getDataSource();
        try {
            Connection connection = db.getConnection();
            PreparedStatement preparedStatement;

            StringBuilder sqlCmd = new StringBuilder("SELECT * FROM users");

            if (top != null && skip != null) {
                sqlCmd.append(" LIMIT ? OFFSET ?");
                preparedStatement = connection.prepareStatement(sqlCmd.toString());
                preparedStatement.setInt(1, Integer.parseInt(top));
                preparedStatement.setInt(2, Integer.parseInt(skip));
            } else {
                preparedStatement = connection.prepareStatement(sqlCmd.toString());
            }

            ResultSet userResult = preparedStatement.executeQuery();
            while (userResult.next()) {
                users.add(new User(
                    userResult.getString("name"),
                    userResult.getString("email"),
                    userResult.getInt("uid")));
            }
            connection.close();
        } catch (SQLException throwables) {
            throw new ServerErrorException("Failed getting all users.");
        }
        return users;
    }

    public User createUser(String name, String email) throws ServerErrorException {
        User user = null;
        PGSimpleDataSource db = getDataSource();
        Connection connection = null;
        try {
            connection = db.getConnection();
            connection.setAutoCommit(false);
            String sqlCmd = "INSERT INTO users(name, email) VALUES (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlCmd);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            if (preparedStatement.executeUpdate() == 1) {
                sqlCmd = "SELECT * FROM users ORDER BY uid DESC LIMIT 1;";
                ResultSet userResult = connection.createStatement().executeQuery(sqlCmd);
                if (userResult.next()) {
                    user = new User(
                        userResult.getString("name"),
                        userResult.getString("email"),
                        userResult.getInt("uid"));
                }
                connection.commit();
            }

            preparedStatement.close();
            connection.setAutoCommit(true);

        } catch (SQLException throwable) {
            throw new ServerErrorException("Failed creating user.");
        }
        return user;
    }

}
