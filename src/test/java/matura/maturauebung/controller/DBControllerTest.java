package matura.maturauebung.controller;

import org.junit.jupiter.api.*;

import javax.xml.transform.Result;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DBControllerTest {


    private Connection connect(String db) throws SQLException {
        Connection con = null;

        if (db.length() <= 0) {
            con = DriverManager.getConnection("jdbc:mysql://localhost:4306", "root", "roor");
        } else {
            con = DriverManager.getConnection("jdbc:mysql://localhost:4306/" + db, "matura", "matura");
        }

        return con;
    }


    @Test
    @Order(1)
    void connectToDb() {
        Assertions.assertDoesNotThrow(() -> connect("matura"));
    }

    @Test
    @Order(2)
    void selectSum() {
        Assertions.assertDoesNotThrow(() -> {

            Connection c = connect("matura");

            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM kunde");

            if (r.next()) {
                Assertions.assertEquals(r.getString("name"), "SEW");
            }
            s.close();
            r.close();
        });
    }


    @Test
    @Order(2)
    void insertSum() {
        Assertions.assertDoesNotThrow(() -> {

            Connection c = connect("matura");

            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("INSERT INTO kunde (name) VALUES ('hans')");

            if (r.next()) {
                Assertions.assertEquals(r.getString("name"), "SEW");
            }
            s.close();
            r.close();
        });
    }

    @Test
    @Order(3)
    void deletesum() {
        Assertions.assertDoesNotThrow(() -> {

            Connection c = connect("matura");

            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("DELETE FROM kunde WHERE name = 'hans'");

            if (r.next()) {
                Assertions.assertNull(r.getString("name"), "");
            }
            s.close();
            r.close();
        });
    }
}