package pt.isel.ls.utils;

import static java.lang.System.getenv;
import org.postgresql.ds.PGSimpleDataSource;

public class Utils {

    public static PGSimpleDataSource getDataSource() {
        PGSimpleDataSource postgres = new PGSimpleDataSource();
        postgres.setURL(getenv("JDBC_DATABASE_URL"));
        return postgres;
    }
}