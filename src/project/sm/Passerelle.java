package project.sm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class Passerelle {

    
    
    public Passerelle() {
    }

    // CONNECTION A LA BASE DE DONNEES
    public static Connection connexionBdd() throws SQLException {
        String url = "jdbc:postgresql://192.168.1.245:5432/slam2021_stockmedicaments_seret";
        String user = "seret";
        String passwd = "seret";
        Connection conn = (Connection) DriverManager.getConnection(url, user, passwd);
        Statement state = conn.createStatement();
        return conn;
    }

    
}
