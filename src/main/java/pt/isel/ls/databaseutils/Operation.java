package pt.isel.ls.databaseutils;

import java.sql.Connection;
import pt.isel.ls.exceptions.AppException;
import pt.isel.ls.results.RequestResult;

public interface Operation {
    RequestResult<?> execute(Connection conn) throws AppException;
}
