package pt.isel.ls.models;

import java.sql.*;
import java.util.LinkedList;
import org.postgresql.ds.PGSimpleDataSource;
import pt.isel.ls.exceptions.ServerErrorException;
import pt.isel.ls.models.domainclasses.Route;
import static pt.isel.ls.utils.Utils.getDataSource;

public class RoutesModel {

    public Route getRouteById(String rid) throws ServerErrorException {
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
            throw new ServerErrorException("Server Error! Fail getting Routes.");
        }
        return route;
    }

    public LinkedList<Route> getAllRoutes(String skip, String top) throws ServerErrorException {
        LinkedList<Route> routes = new LinkedList<>();
        PGSimpleDataSource db = getDataSource();
        try {
            Connection connection = db.getConnection();
            PreparedStatement preparedStatement;

            StringBuilder sqlCmd = new StringBuilder("SELECT * FROM routes");

            if(top != null && skip != null) {
                sqlCmd.append(" LIMIT ? OFFSET ?");
                preparedStatement = connection.prepareStatement(sqlCmd.toString());
                preparedStatement.setInt(1, Integer.parseInt(top));
                preparedStatement.setInt(2, Integer.parseInt(skip));
            }else {
                preparedStatement = connection.prepareStatement(sqlCmd.toString());
            }

            ResultSet routeResult = preparedStatement.executeQuery();
            while (routeResult.next()) {
                routes.add(new Route(
                    routeResult.getInt("rid"),
                    routeResult.getInt("distance"),
                    routeResult.getString("startLocation"),
                    routeResult.getString("endLocation")));
            }
            connection.close();
        } catch (SQLException throwable) {
            throw new ServerErrorException("Server Error! Fail getting Routes.");
        }
        return routes;
    }

    public Route createRoute(String startLocation, String endLocation, String distance) throws ServerErrorException {
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
            if (preparedStatement.executeUpdate() == 1) {
                sqlCmd = "SELECT * FROM routes ORDER BY rid DESC LIMIT 1;";
                ResultSet routeResult = connection.createStatement().executeQuery(sqlCmd);
                if (routeResult.next()) {
                    route = new Route(
                            routeResult.getInt("rid"),
                            routeResult.getInt("distance"),
                            routeResult.getString("startlocation"),
                            routeResult.getString("endlocation"));
                }
                connection.commit();
            }

            preparedStatement.close();
            connection.setAutoCommit(true);
            connection.close();

        } catch (SQLException throwables) {
            throw new ServerErrorException("Server Error! Fail getting Route.");
        }
        return route;
    }

}
