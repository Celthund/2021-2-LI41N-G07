package pt.isel.ls.databaseutils;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.exceptions.ServerErrorException;
import pt.isel.ls.results.RequestResult;

public class TransactionManager {

    private final DataSource dt;

    public TransactionManager(DataSource dt) {
        this.dt = dt;
    }

    public RequestResult<?> execute(Operation operation) throws AppException {
        RequestResult<?> result;
        Connection connection = null;
        try {
            connection = dt.getConnection();
            connection.setAutoCommit(false);
            result = operation.execute(connection);
            connection.commit();
            connection.setAutoCommit(true);
            connection.close();
        } catch (SQLException exc) {
            try {
                connection.rollback();
            } catch (SQLException ignored) {
                //do nothing
            }
            throw new ServerErrorException("Database Error.");
        } catch (AppException exception) {
            try {
                connection.rollback();
            } catch (SQLException ignored) {
                //do nothing
            }
            throw exception;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ignored) {
                    //do nothing
                }
            }
        }
        return result;
    }
}