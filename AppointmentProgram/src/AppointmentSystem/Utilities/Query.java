/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppointmentSystem.Utilities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static AppointmentSystem.Utilities.JDBConnection.conn;

/**
 *
 * @author Eli Armstrong
 * A class to execute query statements
 */
public class Query {
    
    // Holds a string for the query statements
    private static String query;
    // The statement to execute query statements
    private static Statement state;
    
    // -------------------------------------------------------------------------
    
    /**
     * @param queryString the query statement to execute 
     * @return null if no result is returned. If a result is expected then a 
     * ResultSet will be returned 
     */
    public static ResultSet makeQuery(String queryString){
        
        query = queryString;
        
        ResultSet result = null;
        
        try{
            // Create statement object
            state = conn.createStatement();
            
            // Determine query execution
            if(query.toUpperCase().startsWith("SELECT") ){
                result = state.executeQuery(query);
            } else{
                state.executeUpdate(query);
            }
            
        }catch(SQLException ex){
            System.out.println(ex.getLocalizedMessage());
        }
        
        return result;
    }
}
