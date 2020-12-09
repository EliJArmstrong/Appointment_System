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
import AppointmentSystem.View_Controller.CustomerController;

/**
 *
 * @author Eli Armstrong
 */
public class Customer {
    
    private int customerId;       // The id of the customer from the DB
    private String customerName;  // The name of the customer
    private int addressId;        // The ID of the address from the DB
    private Address address;      // The address object related to this object
    private int active;           // The active status of the object
    private Timestamp createDate; // The time/date of the object being created
    private String createdBy;     // The user that created the object
    private Timestamp lastUpdate; // When the object was last modified
    private String lastUpdateby;  // The users who last modified the object.

    // -------------------------------------------------------------------------
    
    /**
     * Default Constructor
     */
    public Customer(){
        address = new Address();
    }
    
    // -------------------------------------------------------------------------
    /**
     * Overload Constructor
     * @param customer an AddCustomerController object
     */
    public Customer(AddCustomerController customer){
        customerName = customer.getCustomerName();
        address = new Address(customer);        
    }
    
    // -------------------------------------------------------------------------
    /**
     * Overload Constructor
     * @param result
     * @throws java.sql.SQLException
     */
    public Customer(ResultSet result) throws SQLException{
        customerName = result.getString("customerName");
        customerId = result.getInt("customerId");
        addressId = result.getInt("addressId");
        address = new Address(result);        
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * This method takes in a CustomerController and updates the info. Then 
     * sends the info to the server.
     * @param customer The CustomerController object 
     */
    public void updateCustomerToServer(CustomerController customer){
        customerName = customer.getCustomerName();
        address.setAddress(customer.getAddress());
        address.setAddress2(customer.getAddress2());
        address.setPostalCode(customer.getPostCode());
        address.setPhone(customer.getPhoneNumber());
        address.setCityId(customer.getCityCode());
        sendUpdateInfoToServer();
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Sends the data to the server via a query. 
     */
    private void sendUpdateInfoToServer(){
        Query.makeQuery("UPDATE customer SET customerName = '" + customerName + 
                "', lastUpdate = NOW(), lastUpdateBy = '" + 
                User.currentUser.getUserName() + "' WHERE customerId = " +
                customerId + ";");
        
        Query.makeQuery("UPDATE address SET address = '" + address.getAddress() 
                + "', address2 = '" + address.getAddress2() + "', cityId = " +
                address.getCityId() + ", postalCode = '" + 
                address.getPostalCode() + "', phone = '" + address.getPhone() +
                "', lastUpdate = NOW(), lastUpdateBy = '" + 
                User.currentUser.getUserName() + "' WHERE  addressId = " +
                addressId + ";");
    }
    
    // -------------------------------------------------------------------------
    
    public void deleteCustomerFromServer(){
        Query.makeQuery("DELETE FROM customer WHERE customerId = " + customerId + ";");
        Query.makeQuery("DELETE FROM address WHERE addressId = " + addressId + ";");
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Adds a customer to the server.
     */
    public void addCustomerToServer(){
        
        address.addAddressToServer(); // Adds the address to the server first.
        
        Query.makeQuery("INSERT INTO customer(customerName, addressId, active, "
                + "createDate, createdBy, lastUpdate, lastUpdateBy)"
                + "VALUES('" + customerName + "', LAST_INSERT_ID(), " + "1,"
                + " NOW(), '" + User.currentUser.getUserName() + "', NOW(), '" +
                User.currentUser.getUserName() + "');");
    }
    
    /**
     * Gets the customer id as it related to the data base.
     * @return an Int
     */
    public int getCustomerId() {
        return customerId;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Set the customer ID
     * @param customerId an int
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Gets the customers id
     * @return a string
     */
    public String getCustomerName() {
        return customerName;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Sets the customers name in the object.
     * @param customerName a string
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Gets the address id that related to this object
     * @return an int
     */
    public int getAddressId() {
        return addressId;
    }

    // -------------------------------------------------------------------------
    
    /**
     * set the address Id for this object.
     * @param addressId an int
     */
    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    // -------------------------------------------------------------------------
    
    /**
     * get the address object that relates to this object.
     * @return an address object
     */
    public Address getAddress() {
        return address;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Sets the address object
     * @param address an address object
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    // -------------------------------------------------------------------------
    
    /**
     * gets the active status of the object.
     * @return an int
     */
    public int getActive() {
        return active;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Sets the active status if the object.
     * @param active an int
     */
    public void setActive(int active) {
        this.active = active;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Gets the date/time of when the object was created
     * @return a timestamp
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Sets the created date/time of the object.
     * @param createDate a timestamp
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    // -------------------------------------------------------------------------
    
    
    /**
     * Gets the user who created this object
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
     * Gets the date/time of when the object was last modified.
     * @return a Timestamp
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Sets the date/time of when the object was last modified.
     * @param lastUpdate a timestamp
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Gets the user who last updated the object.
     * @return a String
     */
    public String getLastUpdateby() {
        return lastUpdateby;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Sets the user who lasted updated the object. 
     * @param lastUpdateby a string
     */
    public void setLastUpdateby(String lastUpdateby) {
        this.lastUpdateby = lastUpdateby;
    }
    
    
    
}
