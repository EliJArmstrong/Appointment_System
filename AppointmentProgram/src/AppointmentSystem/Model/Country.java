/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppointmentSystem.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author Eli Armstrong
 */
public class Country {
    
    private int countryId;         // The ID of the country as it is in the DB
    private String countryName;    // Holds the name of the country
    private Timestamp createdDate; // The date/time when this was created
    private String createdBy;      // The user who placed the object in the DB
    private Timestamp lastUpdate;  // The date/time when this was last modified
    private String lastUpdateBy;   // Who last modified this object

    // -------------------------------------------------------------------------
    
    /**
     * Default Constructor
     */
    Country(){
        
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Overload Constructor
     * @param result ResultSet object
     * @throws java.sql.SQLException
     */
    public Country(ResultSet result) throws SQLException{
        countryId = result.getInt("countryId");
        countryName = result.getString("country");
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Gets the country ID variable
     * @return an int
     */
    public int getCountryId() {
        return countryId;
    }

    // -------------------------------------------------------------------------
    
    /**
     * sets the country id for this object
     * @param countryId  an int
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Gets the country name if the object
     * @return a string
     */
    public String getCountryName() {
        return countryName;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Sets the country name of the object
     * @param countryName a string
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Gets a timestamp of when the object was created.
     * @return a timestamp
     */
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Sets the created date.
     * @param createdDate a time date
     */
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Gets a string of the user name who created the object.
     * @return a string
     */
    public String getCreatedBy() {
        return createdBy;
    }

    // -------------------------------------------------------------------------
    
    
    /**
     * Sets the user who created the object.
     * @param createdBy a string
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    // -------------------------------------------------------------------------
    
    
    /**
     * Gets the date/time when the object was last modified.
     * @return a timestamp
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Sets the dame/time when the object was modified.
     * @param lastUpdate a timestamp
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Gets the users who last updated the object .
     * @return a string
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    // -------------------------------------------------------------------------
    
    /**
     * sets the user who last updated the object
     * @param lastUpdateBy a string
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
    
    
    
    
}
