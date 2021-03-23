package pt.isel.ls.utils;

import org.postgresql.ds.PGSimpleDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Utils {

    private static PGSimpleDataSource db = null;

    public static PGSimpleDataSource getDataSource(){
        Properties configs = new Properties();
        FileInputStream config_file = null;
        PGSimpleDataSource postgres = null;
        try {
            config_file = new FileInputStream("db.properties");
            configs.load(config_file);
            postgres = new PGSimpleDataSource();
            postgres.setURL(configs.getProperty("PSQL_DB_URL"));
            postgres.setUser(configs.getProperty("PSQL_DB_USERNAME"));
            postgres.setPassword(configs.getProperty("PSQL_DB_PASSWORD"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return postgres;
    }

}
