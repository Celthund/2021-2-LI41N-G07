package pt.isel.ls.mappers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import pt.isel.ls.exceptions.ServerErrorException;
import pt.isel.ls.mappers.domainclasses.Route;

public class RoutesMapper {

    public Route getRouteById(String rid, Connection connection) throws ServerErrorException {
        Route route = null;
        try {
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
        } catch (SQLException throwable) {
            throw new ServerErrorException("Failed getting route with id = " + rid + ".");
        }
        return route;
    }

    public LinkedList<Route> getAllRoutes(String skip, String top, Connection connection) throws ServerErrorException {
        LinkedList<Route> routes = new LinkedList<>();
        try {
            PreparedStatement preparedStatement;

            StringBuilder sqlCmd = new StringBuilder("SELECT * FROM routes");

            if (top != null && skip != null) {
                sqlCmd.append(" LIMIT ? OFFSET ?");
                preparedStatement = connection.prepareStatement(sqlCmd.toString());
                preparedStatement.setInt(1, Integer.parseInt(top));
                preparedStatement.setInt(2, Integer.parseInt(skip));
            } else {
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
        } catch (SQLException throwable) {
            throw new ServerErrorException("Failed getting all routes.");
        }
        return routes;
    }

    public Route createRoute(String startLocation, String endLocation, String distance,
                             Connection connection) throws ServerErrorException {
        Route route = null;
        try {
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
            }
            preparedStatement.close();
        } catch (SQLException throwables) {
            throw new ServerErrorException("Failed creating route.");
        }
        return route;
    }

}
