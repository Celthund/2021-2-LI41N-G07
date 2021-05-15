package pt.isel.ls.utils;

import org.postgresql.ds.PGSimpleDataSource;
import static java.lang.System.getenv;


public class Utils {

    public static PGSimpleDataSource getDataSource() {
        PGSimpleDataSource postgres = new PGSimpleDataSource();
        postgres.setURL(getenv("PSQL_DB_URL"));
        postgres.setUser(getenv("PSQL_DB_USERNAME"));
        postgres.setPassword(getenv("PSQL_DB_PASSWORD"));
        return postgres;
    }

}
