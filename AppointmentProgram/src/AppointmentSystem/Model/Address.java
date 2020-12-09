/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppointmentSystem.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import AppointmentSystem.Utilities.Query;
import AppointmentSystem.View_Controller.AddCustomerController;

/**
 *
 * @author Eli Armstrong
 * This class represents an address.
 */
public class Address {
    
    private int addressId;        // Holds the ID of this object in the server
    private String address;       // Holds the first line of the address
    private String address2;      // Holds the 2nd line of address
    private int cityId;           // The city ID related to this address
    private City city;            // Holds the city object that relates to this
    private String postalCode;    // Holds the postal code for the address
    private String phone;         // Holds the phone number of the address
    private Timestamp createDate; // The date when the object was created
    private String createdBy;     // The user who created the object
    private Timestamp lastUpdate; // When the object was last updated
    private String lastUpdatedBy; // The user who last updated the object

    
    // -------------------------------------------------------------------------
    
    /**
     * Default Constructor
     */
    public Address(){
        city = new City();
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Overload Constructor
     * @param customer AddCustomerController object
     */
    public Address(AddCustomerController customer){
        address = customer.getAddress();
        address2 = customer.getAddress2();
        cityId = customer.getCityCode();
        phone = customer.getPhoneNumber();
        postalCode = customer.getPostCode();
        
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Overload Constructor
     * @param result ResultSet object
     * @throws java.sql.SQLException
     */
    public Address(ResultSet result) throws SQLException{
        addressId = result.getInt("addressId");
        address = result.getString("address");
        address2 = result.getString("address2");
        cityId = result.getInt("cityId");
        phone = result.getString("phone");
        postalCode = result.getString("postalCode");
        
        city = new City(result);
        
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Adds this address to the server.
     */
    public void addAddressToServer(){
         Query.makeQuery("INSERT INTO address(address, address2, cityId, "
                + "postalCode, phone, createDate, createdBy, lastUpdate, "
                + "lastUpdateBy) VALUES( '" + address + "', '" +
                address2 + "', " + cityId + ", '" + postalCode + "', '" + phone
                 + "', NOW(), '" + User.currentUser.getUserName() +
                 "', NOW(), '" + User.currentUser.getUserName() + "');");
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * gets the address id 
     * @return an int that is the address id
     */
    public int getAddressId() {
        return addressId;
    }

    // -------------------------------------------------------------------------
    
    /**
     * sets the address id.
     * @param addressId an int to set the addressId
     */
    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    // -------------------------------------------------------------------------
    
    /**
     * gets the address variable 
     * @return a string 
     */
    public String getAddress() {
        return address;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Sets the address variable 
     * @param address a string that represents an address 
     */
    public void setAddress(String address) {
        this.address = address;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Gets the address 2 variable
     * @return a string that represents the 2nd line of an address if needed. 
     */
    public String getAddress2() {
        return address2;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Sets the address variable
     * @param address2 a string that represents the 2nd line of an address if 
     * needed.
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Gets the City ID
     * @return an int that represents the id of the city in the server.
     */
    public int getCityId() {
        return cityId;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Sets the city ID 
     * @param cityId an int that represents the id of the city in the server.
     */
    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Gets the city related to this Address
     * @return A city object
     */
    public City getCity() {
        return city;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Sets the city object related to this address
     * @param city a city object
     */
    public void setCity(City city) {
        this.city = city;
    }

    // -------------------------------------------------------------------------
    
    /**
     * gets the postal code of this address.
     * @return A string 
     */
    public String getPostalCode() {
        return postalCode;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Sets the postal code
     * @param postalCode a string
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Gets the phone number
     * @return A string
     */
    public String getPhone() {
        return phone;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Sets the phone number of this object
     * @param phone a string 
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // -------------------------------------------------------------------------
   /**
    * Gets the crateDate variable
    * @return A Timestamp
    */
    public Timestamp getCreateDate() {
        return createDate;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Sets the Created date variable
     * @param createDate a timestamp 
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Gets the createdBy string this is the users who created the address.
     * @return A string
     */
    public String getCreatedBy() {
        return createdBy;
    }

    
    /**
     * Set the created by variable. This is the user who created the address.
     * @param createdBy A string
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Gets the last update variable. This is the date when the address was last
     * modified.
     * @return A timestamp
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Set the last update variable. This is the date when the address was last
     * modified.
     * @param lastUpdate A timestamp
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Gets the last update by variable. This is the users who last modified this 
     * object.
     * @return A string
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    // -------------------------------------------------------------------------
    
    
    /**
     * Sets the last update by variable. This is the users who last modified this 
     * object.
     * @param lastUpdatedBy a string
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
    
    // -------------------------------------------------------------------------
    
    
}
