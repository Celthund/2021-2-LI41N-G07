package pt.isel.ls.utils;

import static java.lang.System.getenv;
import org.postgresql.ds.PGSimpleDataSource;

public class Database {
    private static PGSimpleDataSource dataSource = null;

    public static PGSimpleDataSource getDataSource() {
        if (dataSource == null) {
            dataSource = new PGSimpleDataSource();
            dataSource.setURL(getenv("JDBC_DATABASE_URL"));
        }
        return dataSource;
    }
}