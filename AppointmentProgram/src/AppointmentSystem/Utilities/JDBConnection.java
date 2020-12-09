/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppointmentSystem.Utilities;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Eli Armstrong
 * This class is used to create a connection to the mySQL server
 */
public class JDBConnection {
    
    // The data base name
    private static final String DATA_BASE_NAME = "U06Kxg";
    // The data base URL
    private static final String DB_URL  = "jdbc:mysql://52.206.157.109/" + DATA_BASE_NAME;
    // The data base user name
    private static final String USER_NAME = "U06Kxg";
    // The password for the data base 
    private static final String PASSWORD = "53688790615";
    // The data base driver
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    // A static method to hold the Connection to be used for by the program 
    static Connection conn;
    
    // -------------------------------------------------------------------------
    
    /**
     * Creates a connection to the data base
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws Exception 
     */
    public static void makeConnection() throws ClassNotFoundException, SQLException, Exception{
        Class.forName(DRIVER);
        conn = (Connection) DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
        System.out.println("Connection sucessful.");
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Closes the connection to the data base
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws Exception 
     */
    public static void closeConnection() throws ClassNotFoundException, SQLException, Exception{
        conn.close();
        System.out.println("Connection Closed.");
    }
    
}
