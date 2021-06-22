package pt.isel.ls.utils;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.results.RequestResult;

public class TransactionManager {

    private final DataSource dt;

    public TransactionManager(DataSource dt) {
        this.dt = dt;
    }

    public RequestResult<?> execute(Operation operation) {
        RequestResult<?> result = null;
        Connection connection = null;
        try {
            connection = dt.getConnection();
            connection.setAutoCommit(false);
            result = operation.execute(connection);
            connection.commit();
            connection.setAutoCommit(true);
            connection.close();
        } catch (SQLException | AppException throwables) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }
        return result;
    }
}