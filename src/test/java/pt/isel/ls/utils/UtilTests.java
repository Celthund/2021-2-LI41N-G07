package pt.isel.ls.utils;

import org.junit.Assert;
import org.junit.Test;
import org.postgresql.ds.PGSimpleDataSource;
import org.postgresql.util.PSQLException;
import pt.isel.ls.exceptions.*;
import pt.isel.ls.handlers.users.GetUserByIdHandler;
import pt.isel.ls.models.UserModel;
import pt.isel.ls.models.domainclasses.User;
import pt.isel.ls.request.RequestHandler;
import pt.isel.ls.results.RequestResult;
import pt.isel.ls.results.users.CreateUserResult;
import pt.isel.ls.routers.HandlerRouter;
import pt.isel.ls.request.Method;
import pt.isel.ls.request.Request;
import java.sql.*;
import java.util.LinkedList;
import java.util.Optional;
import static pt.isel.ls.utils.Database.getDataSource;

public class UtilTests {

    @Test
    public void test_connection() throws SQLException {
        PGSimpleDataSource db = getDataSource();
        Connection connection = db.getConnection();
        connection.close();
    }

    @Test(expected = PSQLException.class)
    public void test_wrong_server() throws SQLException {
        PGSimpleDataSource db = getDataSource();
        db.setServerNames(new String[] {"Test_Host"});
        Connection conn = db.getConnection();
        conn.close();
    }

    @Test
    public void test_connection_config_file() throws SQLException {
        PGSimpleDataSource db = getDataSource();
        Connection conn = db.getConnection();
        conn.close();
    }

    @Test(expected = PSQLException.class)
    public void test_wrong_user() throws SQLException {
        PGSimpleDataSource db = getDataSource();
        db.setServerNames(new String[] {"Server1"});
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

    @Test
    public void test_add_users() throws SQLException, ServerErrorException {
        PGSimpleDataSource db = getDataSource();
        Connection conn = db.getConnection();
        UserModel model = new UserModel();
        conn.setAutoCommit(false);
        model.createUser("name=test", "email=test@mailtest.com", conn);

        conn.rollback();
        conn.setAutoCommit(true);
        conn.close();
    }

    @Test
    public void test_get_users_id() throws SQLException, ServerErrorException {
        PGSimpleDataSource db = getDataSource();
        Connection conn = db.getConnection();
        UserModel model = new UserModel();
        conn.setAutoCommit(false);
        User userCreate = model.createUser("name=test", "email=test@mailtest.com", conn);
        User userQuery = model.getUserById(Integer.toString(userCreate.id),  conn);

        Assert.assertEquals("User {name='name=test', email='email=test@mailtest.com', id=" + userCreate.id + "}", userQuery.toString());

        conn.rollback();
        conn.setAutoCommit(true);
        conn.close();
    }

    @Test(expected = pt.isel.ls.exceptions.ServerErrorException.class)
    public void test_get_repeated_user_id() throws SQLException, ServerErrorException {
        PGSimpleDataSource db = getDataSource();
        Connection conn = db.getConnection();
        UserModel model = new UserModel();
        conn.setAutoCommit(false);
        model.createUser("name=test", "email=test@mailtest.com", conn);
        model.createUser("name=test", "email=test@mailtest.com", conn);

        conn.rollback();
        conn.setAutoCommit(true);
        conn.close();
    }

    @Test
    public void test_get_users() throws SQLException, ServerErrorException {
        PGSimpleDataSource db = getDataSource();
        Connection conn = db.getConnection();
        UserModel model = new UserModel();
        conn.setAutoCommit(false);
        PreparedStatement statement;
        String cmd = "DROP TABLE if exists activities;"
                + "DROP TABLE if exists routes;"
                + "DROP TABLE if exists sports;"
                + "DROP TABLE if exists users;";

        statement = conn.prepareStatement(cmd);
        statement.executeUpdate();
        cmd = "create table if not exists users(" +
                "  uid serial primary key," +
                "  name varchar(80) not null," +
                "  email varchar(80) unique not null" +
                ");";
        statement = conn.prepareStatement(cmd);
        statement.executeUpdate();


        model.createUser("test1", "test1@mailtest.com", conn);
        model.createUser("test2", "test2@mailtest.com", conn);
        model.createUser("test3", "test3@mailtest.com", conn);
        model.createUser("test4", "test4@mailtest.com", conn);

        LinkedList<User> userQuery = model.getAllUsers("0", "5",  conn);

        Assert.assertEquals("User {name='test1', email='test1@mailtest.com', id=1}", userQuery.remove().toString());
        Assert.assertEquals("User {name='test2', email='test2@mailtest.com', id=2}", userQuery.remove().toString());
        Assert.assertEquals("User {name='test3', email='test3@mailtest.com', id=3}", userQuery.remove().toString());
        Assert.assertEquals("User {name='test4', email='test4@mailtest.com', id=4}", userQuery.remove().toString());

        conn.rollback();
        conn.setAutoCommit(true);
        conn.close();
    }



    private void createTableForTests(Connection connection) throws SQLException {
        String create = "drop table if exists activitiesTest;" +
                "drop table if exists usersTest cascade;" +
                "drop table if exists routesTest cascade;" +
                "drop table if exists sportsTest cascade;" +
                "create table if not exists routesTest (" +
                "  rid serial primary key," +
                "  startlocation varchar(80)," +
                "  endlocation varchar(80)," +
                "  distance int\n" +
                ");" +
                "create table if not exists usersTest (" +
                "  uid serial primary key," +
                "  name varchar(80) not null," +
                "  email varchar(80) unique not null" +
                ");" +
                "create table if not exists sportsTest (" +
                "   sid serial primary key," +
                "   name varchar(80) not null," +
                "   description varchar(120) not null" +
                ");" +
                "create table if not exists activitiesTest (" +
                "    aid serial primary key," +
                "    uid int not null references users (uid)," +
                "    rid int references routes (rid)," +
                "    sid int references sports (sid)," +
                "    date date not null," +
                "    duration bigint not null," +
                "    ts_deleted timestamp" +
                ");";

        Statement statement = connection.createStatement();

        statement.execute(create);
    }

    private void addDataToTable(Connection connection) throws SQLException {
        String create = "INSERT into usersTest (name, email) VALUES ('Ze Antonio','A123456@alunos.isel.pt');" +
                        "INSERT into usersTest (name, email) VALUES ('Luis Alves','luis.alves@alunos.isel.pt');"
                + "INSERT into usersTest (name, email) VALUES ('Jorge Simoes','jorge.simoes@alunos.isel.pt');"
                + "INSERT into sportsTest (name, description) values ('Cycling', 'Cycling');"
                + "INSERT into sportsTest (name, description) values ('Spinning', 'Indor Spinning');"
                + "INSERT into sportsTest (name, description) values ('Treadmill', 'Treadmill');"
                + "INSERT into routesTest (startlocation, endlocation, distance) VALUES ('Lisboa', 'Porto', 274);" +
                "INSERT into routesTest (startlocation, endlocation, distance) VALUES ('Faro', 'Madrid', 1274);" +
                "INSERT into routesTest (startlocation, endlocation, distance) VALUES ('Lisboa', 'Lisboa', 40075);"
                + "INSERT into activitiesTest (uid, rid, sid, date, duration, ts_deleted) VALUES (1, 1, 1 , '2014-5-17' ,19019,NULL);" +
                "INSERT into activitiesTest (uid, rid, sid, date, duration, ts_deleted) VALUES (1, 2, 2, '2007-4-16',33592,NULL);" +
                "INSERT into activitiesTest (uid, rid, sid, date, duration, ts_deleted) VALUES (2, 3, 3, '2017-6-22',170965,NULL);";


        Statement statement = connection.createStatement();

        statement.execute(create);
    }

    @Test
    public void test_add_router() throws RouteAlreadyExistsException {
        new HandlerRouter().addRoute("GET","/users/1", new GetUserByIdHandler());
    }

    @Test
    public void test_find_router() throws AppException {
        Request request = new Request(Method.GET, "/users/1");
        new HandlerRouter().addRoute("GET","/users/1", new GetUserByIdHandler());

        HandlerRouter handlerRouter = new HandlerRouter();
        handlerRouter.addRoute("GET","/users/1", new GetUserByIdHandler());
        handlerRouter.findRoute(request);
    }

    @Test(expected = org.postgresql.util.PSQLException.class)
    public void test_insert_existing_user_manually() throws SQLException {
        PGSimpleDataSource db = getDataSource();
        Connection connection = db.getConnection();

        try {
            connection.setAutoCommit(false);
            createTableForTests(connection);
            addDataToTable(connection);
            PreparedStatement statement = null;
            String sqlCmd = "DELETE FROM user WHERE email = 'A123456@alunos.isel.pt';";
            statement = connection.prepareStatement(sqlCmd);
            statement.executeUpdate();
            sqlCmd = "INSERT into users (name, email) VALUES ('Ze Antonio','A123456@alunos.isel.pt');";
            statement = connection.prepareStatement(sqlCmd);
            statement.executeUpdate();
            int result = statement.executeUpdate();
            Assert.assertEquals(1, result);

        } finally {
            connection.rollback();
            connection.setAutoCommit(true);
            connection.close();
        }
    }

    @Test
    public void test_existing_routing() throws AppException {
        HandlerRouter handlerRouter = new HandlerRouter();
        handlerRouter.addRoute("GET", "/abc/{id}/123", request -> Optional.of(new Result(200, null, "Success")));
        Request request = new Request(Method.getMethod("GET"), "/abc/2/123");
        RequestHandler handler = handlerRouter.findRoute(request);
        Optional<RequestResult<?>> res = handler.execute(request);
        assert res.isPresent();
    }

    @Test(expected = RouteNotFoundException.class)
    public void test_non_existing_routing() throws AppException {
        HandlerRouter handlerRouter = new HandlerRouter();
        Request request = new Request(Method.getMethod("GET"), "/abc/2/123");
        handlerRouter.findRoute(request);
    }

    @Test(expected = RouteAlreadyExistsException.class)
    public void test_already_existing_routing() throws AppException {
        HandlerRouter handlerRouter = new HandlerRouter();
        Optional<RequestResult<?>> result = Optional.of(new Result(200, null, "Success"));
        handlerRouter.addRoute("GET", "/abc/{id}/123", request -> result);
        handlerRouter.addRoute("GET", "/abc/{id}/123", request -> result);
    }

    class Result extends RequestResult<Object> {

        public Result(int status, Object data, String message) {
            super(status, data, message);
        }
    }
}
