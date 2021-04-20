package pt.isel.ls.Utils;

import org.junit.Test;
import org.postgresql.ds.PGSimpleDataSource;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.SQLException;

import static pt.isel.ls.Utils.Utils.getDataSource;

public class UtilsTests {

    @Test
    public void test_connection_config_file() throws SQLException {
        PGSimpleDataSource db = getDataSource();
        Connection conn = db.getConnection();
        conn.close();
    }

    @Test(expected= PSQLException.class)
    public void test_wrong_server() throws SQLException {
        PGSimpleDataSource db = getDataSource();
        db.setServerNames(new String[]{"Test_Host"});
        Connection conn = db.getConnection();
        conn.close();
    }

    @Test(expected=PSQLException.class)
    public void test_wrong_user() throws SQLException {
        PGSimpleDataSource db = getDataSource();
        db.setUser("Test_User");
        Connection conn = db.getConnection();
        conn.close();
    }

    @Test(expected=PSQLException.class)
    public void test_wrong_password() throws SQLException {
        PGSimpleDataSource db = getDataSource();
        db.setPassword("Test_Password");
        Connection conn = db.getConnection();
        conn.close();
    }



}
