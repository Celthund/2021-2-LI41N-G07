package pt.isel.ls.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.ds.PGSimpleDataSource;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static pt.isel.ls.utils.Utils.getDataSource;


public class UtilsLaTests {

    @Test
    public void test_exists_data_in_students() throws SQLException {
        PGSimpleDataSource db = getDataSource();
        Connection conn = db.getConnection();

        try{
            String sqlCmd = "SELECT COUNT(*) FROM students;";
            Statement statement = conn.createStatement();
            ResultSet result =  statement.executeQuery(sqlCmd);
            result.next();
            Assert.assertEquals(1, result.getInt(1));

        } finally{
            conn.close();
        }
    }

    @Test
    public void test_connection() throws SQLException {
        PGSimpleDataSource db = getDataSource();            // Obter a connection string
        Connection conn = db.getConnection();               // Criar a ligacao ao rdbms

        try{
            String sqlCmd = "SELECT name FROM students WHERE number = 12345";   // Query a executar
            Statement statement = conn.createStatement();       // Criar o comamando a executar
            ResultSet result = statement.executeQuery(sqlCmd);  // Executar query
            result.next();
            Assert.assertEquals("Alice29", result.getString("name"));
        } finally{
            conn.close();                                   // Garantir que se fecha a ligacao
        }
    }
}
