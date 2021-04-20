package pt.isel.ls.Models;


import org.postgresql.ds.PGSimpleDataSource;
import pt.isel.ls.DataClass.User;

import java.sql.*;
import java.util.LinkedList;

import static pt.isel.ls.Utils.Utils.getDataSource;

public class UserModel {

    public User getUserById(String id) {
        User user = null;
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    public LinkedList<User> getAllUsers() {
        LinkedList<User> users = new LinkedList<>();
        PGSimpleDataSource db = getDataSource();
        try {
            Connection connection = db.getConnection();
            Statement statement = connection.createStatement();
            ResultSet userResult = statement.executeQuery("SELECT * FROM users");
            while (userResult.next())
                users.add(new User(
                        userResult.getString("name"),
                        userResult.getString("email"),
                        userResult.getInt("uid")));
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    public User createRoute(String name, String email) {
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
            if (preparedStatement.executeUpdate() == 1){
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

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return user;
    }

}
