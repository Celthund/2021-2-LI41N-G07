package pt.isel.ls.utils;

import static java.lang.System.getenv;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Utils {

    public static PGSimpleDataSource getDataSource() {
        PGSimpleDataSource postgres = new PGSimpleDataSource();
        postgres.setURL(getenv("JDBC_DATABASE_URL"));
        return postgres;
    }
}