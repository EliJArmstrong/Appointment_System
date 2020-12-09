/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppointmentSystem.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eli Armstrong
 */
public class Reports {
    
    private String customerName;
    private String userName;
    private String type;
    private Timestamp start;
    private String month;
    private int count;
    

    public void fillByScheduleData(ResultSet result){
        try {
            customerName = result.getString("customerName");
            type = result.getString("type");
            start = result.getTimestamp("start");
        } catch (SQLException ex) {
            Logger.getLogger(Reports.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void fillByTypeData(ResultSet result){
        try {
            type = result.getString("type");
            count = result.getInt("count");
            month = result.getString("month");
        } catch (SQLException ex) {
            Logger.getLogger(Reports.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void fillByConsultData(ResultSet result){
        try {
            userName = result.getString("userName");
            count = result.getInt("count");
        } catch (SQLException ex) {
            Logger.getLogger(Reports.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getCustomerName() {
        return customerName;
    }

    public String getUserName() {
        return userName;
    }

    public Timestamp getStart() {
        return start;
    }

    public String getType() {
        return type;
    }

    public String getMonth() {
        return month;
    }

    public int getCount() {
        return count;
    }
    
}
