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
public class City {
    
    // Variables
    private int cityId;           // The ID of the city as it relates to the DB
    private String cityName;      // Holds the name of the city
    private int countryId;        // The country id as it relates to the server
    private Timestamp createDate; // The date/time the object was created.
    private String createdBy;     // Who last modified the object
    private Timestamp lastUpdate; // The date/time when the this was modified
    private String lastUpdateBy;  // The user that last updated the object
    private Country country;      // The Country object related to this object

    // -------------------------------------------------------------------------
    
    /**
     * Default Constructor
     */
    City(){
        
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Overload Constructor
     * @param result ResultSet object
     */
    public City(ResultSet result) throws SQLException{
        cityId = result.getInt("cityId");
        cityName = result.getString("city");
        
        country = new Country(result);
    }
    
    
    // -------------------------------------------------------------------------
    
    /**
     * Gets the country object related to this City
     * @return A Country object
     */
    public Country getCountry() {
        return country;
    }

    
    // -------------------------------------------------------------------------
    
    /**
     * Sets the Country object related to the city.
     * @param country an Country object
     */
    public void setCountry(Country country) {
        this.country = country;
    }
    
    
    // -------------------------------------------------------------------------
    
    /**
     * gets the city id
     * @return an int: the city id
     */
    public int getCityId() {
        return cityId;
    }

    // -------------------------------------------------------------------------
    
    /**
     * sets the cityId
     * @param cityId the Id to be set
     */
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    // -------------------------------------------------------------------------
    
    /**
     * gets the city name
     * @return a string: the city's name
     */
    public String getCityName() {
        return cityName;
    }

    // -------------------------------------------------------------------------
    
    /**
     * sets the city name
     * @param cityName the city name to be set 
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    // -------------------------------------------------------------------------
    
    /**
     * gets the country id
     * @return an int: the country id
     */
    public int getCountryId() {
        return countryId;
    }

    // -------------------------------------------------------------------------
    
    /**
     * sets the country's id 
     * @param countryId the country id to be set
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    // -------------------------------------------------------------------------
    
    /**
     * gets the date the city was created
     * @return a Timestamp: created date
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    // -------------------------------------------------------------------------
    
    /**
     * sets the Date when the city was entered in the server
     * @param createDate the date to be set
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    // -------------------------------------------------------------------------
    
    /**
     * The user that entered the city in the server
     * @return a string with who entered the city 
     */
    public String getCreatedBy() {
        return createdBy;
    }

    // -------------------------------------------------------------------------
    
    /**
     * sets the created by string
     * @param createdBy a string t0 set the createdBY variable 
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    // -------------------------------------------------------------------------
    
    /**
     * returns a date with when the city was last updated in the server
     * @return a Timestamp: Last update
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    // -------------------------------------------------------------------------
    
    /**
     * sets the last update variable 
     * @param lastUpdate a date object to to set the last update variable
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    // -------------------------------------------------------------------------
    
    /**
     * This method returns a string with who last update the city in the server
     * @return a string with who last updated the city in the server
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    // -------------------------------------------------------------------------
    
    /**
     * sets the user that last updated the city in the server
     * @param lastUpdateBy sets the user who last updated the the city in the 
     * server
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
}
