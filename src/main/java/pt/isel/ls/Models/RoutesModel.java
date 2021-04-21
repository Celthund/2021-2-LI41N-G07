package pt.isel.ls.Models;

import org.postgresql.ds.PGSimpleDataSource;
import pt.isel.ls.DataClass.Route;
import pt.isel.ls.DataClass.User;

import java.sql.*;
import java.util.LinkedList;

import static pt.isel.ls.Utils.Utils.getDataSource;

public class RoutesModel {

    public Route getRouteById(String rid) {
        Route route = null;
        // Get the configurations to set up the DB connection
        PGSimpleDataSource db = getDataSource();
        try {
            Connection connection = db.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM routes WHERE rid = ?");
            preparedStatement.setInt(1, Integer.parseInt(rid));

            ResultSet routeResult = preparedStatement.executeQuery();
            if (routeResult.next()) {
                route = new Route(
                        routeResult.getInt("rid"),
                        routeResult.getInt("distance"),
                        routeResult.getString("startLocation"),
                        routeResult.getString("endLocation"));
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return route;
    }

    public LinkedList<Route> getAllRoutes() {
        LinkedList<Route> routes = new LinkedList<>();
        PGSimpleDataSource db = getDataSource();
        try {
            Connection connection = db.getConnection();
            Statement statement = connection.createStatement();
            ResultSet routeResult = statement.executeQuery("SELECT * FROM routes");
            while (routeResult.next())
                routes.add(new Route(
                        routeResult.getInt("rid"),
                        routeResult.getInt("distance"),
                        routeResult.getString("startLocation"),
                        routeResult.getString("endLocation")));
            connection.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return routes;
    }

    public Route createRoute(String startLocation,  String endLocation, String distance) {
        Route route = null;
        PGSimpleDataSource db = getDataSource();
        Connection connection = null;
        try {
            connection = db.getConnection();
            connection.setAutoCommit(false);
            String sqlCmd = "INSERT INTO routes(distance, startLocation, endLocation) VALUES (?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlCmd);
            preparedStatement.setInt(1, Integer.parseInt(distance));
            preparedStatement.setString(2, startLocation);
            preparedStatement.setString(3, endLocation);
            if (preparedStatement.executeUpdate() == 1){
                sqlCmd = "SELECT * FROM routes ORDER BY rid DESC LIMIT 1;";
                ResultSet userResult = connection.createStatement().executeQuery(sqlCmd);
                if (userResult.next()) {
                    route = new Route(
                            userResult.getInt("rid"),
                            userResult.getInt("distance"),
                            userResult.getString("startlocation"),
                            userResult.getString("endlocation"));
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
                } catch (SQLException throwable) {
                    throwable.printStackTrace();
                }
            }
        }
        return route;
    }

}
