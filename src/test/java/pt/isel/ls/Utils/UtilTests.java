package pt.isel.ls.Utils;

import org.junit.Assert;
import org.junit.Test;
import org.postgresql.ds.PGSimpleDataSource;
import org.postgresql.util.PSQLException;

import java.sql.*;

import static pt.isel.ls.Utils.Utils.getDataSource;

public class UtilTests {

    @Test
    public void test_connection() throws SQLException {
        PGSimpleDataSource db = getDataSource();
        Connection connection = db.getConnection();
        connection.close();
    }

    @Test(expected = PSQLException.class)
    public void test_wrong_user() throws SQLException {
        PGSimpleDataSource db = getDataSource();
        db.setServerNames(new String[]{"Server1"});
        db.setUser("Wrong UserModel");
        Connection connection = db.getConnection();
        connection.close();
    }

    @Test(expected = PSQLException.class)
    public void test_wrong_password() throws SQLException {
        PGSimpleDataSource db = getDataSource();
        db.setPassword("Wrong password");
        Connection connection = db.getConnection();
        connection.close();
    }


    private void createTableForTests(Connection connection) throws SQLException {
        String create = "drop table if exists students;" +
                        "drop table if exists courses;" +
                        "create table courses (cid serial primary key, name varchar(80));" +
                        "create table students (number int primary key, name varchar(80), course int references courses(cid));";

        Statement statement = connection.createStatement();

        statement.execute(create);

    }

    private void addDataToTable(Connection connection) throws SQLException {
        String create = "insert into courses(name) values ('LEIC');" +
                        "insert into students(course, number, name) values (1, 12345, 'Alice');" +
                        "insert into students(course, number, name) select cid as course, 12346 as number, 'Bob' as name from courses where name = 'LEIC'";

        Statement statement = connection.createStatement();

        statement.execute(create);
    }


    @Test
    public void test_select_permission() throws SQLException {
        PGSimpleDataSource db = getDataSource();
        Connection connection = db.getConnection();

        try {
            connection.setAutoCommit(false);
            createTableForTests(connection);
            addDataToTable(connection);
            String select = "SELECT name FROM students WHERE name = 'Alice'";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(select);
            result.next();
            Assert.assertEquals("Alice", result.getString("name"));

        } finally {
            connection.rollback();
            connection.close();
        }
    }

    @Test
    public void test_insert_permission() throws SQLException {
        PGSimpleDataSource db = getDataSource();
        Connection connection = db.getConnection();

        try {
            connection.setAutoCommit(false);
            createTableForTests(connection);
            addDataToTable(connection);
            String sqlCmd = "INSERT INTO students(course, number, name) VALUES (1, 666, 'Asdrubal');";

            PreparedStatement statement = connection.prepareStatement(sqlCmd);
            int result = statement.executeUpdate();
            Assert.assertEquals(1, result);

        } finally {
            connection.rollback();
            connection.close();
        }
    }

    @Test
    public void test_update_permission() throws SQLException {
        PGSimpleDataSource db = getDataSource();
        Connection connection = db.getConnection();

        try {
            connection.setAutoCommit(false);
            createTableForTests(connection);
            addDataToTable(connection);
            String sqlCmd = "UPDATE students SET name = 'Alvaro' WHERE name LIKE 'Alice';";

            PreparedStatement statement = connection.prepareStatement(sqlCmd);
            int result = statement.executeUpdate();
            Assert.assertEquals(1, result);

        } finally {
            connection.rollback();
            connection.close();
        }
    }

    @Test
    public void test_delete_permission() throws SQLException {
        PGSimpleDataSource db = getDataSource();
        Connection connection = db.getConnection();

        try {
            connection.setAutoCommit(false);
            createTableForTests(connection);
            addDataToTable(connection);
            String sqlCmd = "DELETE FROM students WHERE number = 12345;";

            PreparedStatement statement = connection.prepareStatement(sqlCmd);
            int result = statement.executeUpdate();
            Assert.assertEquals(1, result);

        } finally {
            connection.rollback();
            connection.close();
        }
    }
}