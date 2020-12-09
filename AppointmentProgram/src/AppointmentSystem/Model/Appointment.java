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
import AppointmentSystem.View_Controller.AddAppointmentController;
import AppointmentSystem.View_Controller.AppointmentsController;

/**
 *
 * @author Eli Armstrong
 */
public class Appointment {
    
    private int appointmentId;    // The appointment ID as it related to the DB
    private int customerId;       // The customer id related to this object
    private Customer customer;    // The customer related to this object
    private String customerName;  // The name of the customer
    private int userId;           // The user id the is related to this object
    private User user;            // The user related to the appointment
    private String userName;      // The username of the user.
    private String title;         // The title of the appointment
    private String description;   // The description of the appointment
    private String contact;       // The contact of the meeting
    private String type;          // The type of meeting
    private String url;           // The url of the appointment
    private Timestamp start;      // The date.time of the start of the meeting
    private Timestamp end;        // The date/time of the end of the meeting
    private Timestamp createDate; // When this object was created
    private String createdBy;     // The user who created this object
    private Timestamp lastUpdate; // The date/time of when this was modified
    private String lastUpdateBy;  // The user name of who last modified this
    
    // -------------------------------------------------------------------------
    
    /**
     * Default Constructor
     */
    public Appointment(){
        customer = new Customer();
        user = new User();
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Creates a appointment based of an ResultSet
     * @param result result set
     * @throws java.sql.SQLException
     */
    public Appointment(ResultSet result) throws SQLException{
        appointmentId = result.getInt("appointmentId");
        type = result.getString("type");
        start = result.getTimestamp("start");
        customerId = result.getInt("customerId");
        customerName = result.getString("customerName");
        userName     = result.getString("userName");
        userId       = result.getInt("userId");
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * THis updates this appointment in the server.
     * @param appointment an AppointmentsController
     */
    public void updateThisAppointment(AppointmentsController appointment){
        type = appointment.getMeetingType();
        customerId = appointment.getCustomerId();
        userId = appointment.getUserId();
        start = appointment.getDateTimeUTC();
        
        Query.makeQuery("UPDATE appointment SET customerId = " + customerId + 
                ", userId = " + userId + ", type = '" + type + "', start = '" + 
                start.toString() + "', end = DATE_ADD('" + start.toString() + 
                "', INTERVAL 15 MINUTE), lastUpdate = NOW(), lastUpdateBy = '" +
                User.currentUser.getUserName() + "' WHERE appointmentId = " + appointmentId + ";");
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * When this method is called this object is removed from the server.
     */
    public void deleteThisObject(){
        Query.makeQuery("DELETE FROM appointment WHERE appointmentId = " + appointmentId + ";");
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * This takes in a AddAppointmentController add fills the data of this 
     * object.
     * @param appointment an AddAppointmentController object.
     */
    public Appointment(AddAppointmentController appointment){
        customerId = appointment.getCustomerId();
        userId = appointment.getUserId();
        type = appointment.getMeetingType();
        start = appointment.getDateTimeUTC();
        end = appointment.getDateTimeUTC();
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Adds this object to the server.
     */
    public void addAppointmentToServer(){
        Query.makeQuery("INSERT INTO appointment(customerId, userId, title, "
                + "description, location, contact, type, url, start, end, "
                + "createDate, createdBy, lastUpdate, lastUpdateBy) VALUES(" + 
                customerId + ", " + userId + ", 'N/A', 'N/A', 'N/A', 'N/A', '" +
                type + "', 'N/A', '" + start.toString() + "', DATE_ADD('" + 
                start.toString() +"', INTERVAL 15 MINUTE), NOW(), '" + 
                User.currentUser.getUserName() + "', NOW(), '" + 
                User.currentUser.getUserName() + "');");
    }
    
    // -------------------------------------------------------------------------
    /***
     * Gets the appointment id
     * @return an int
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Sets the appointment id
     * @param appointmentId an int
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Gets the customer id 
     * @return an int
     */
    public int getCustomerId() {
        return customerId;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Sets the customer id
     * @param customerId an int
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Gets the Customer object as is relates to this object
     * @return a customer object
     */
    public Customer getCustomer() {
        return customer;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Sets the customer object
     * @param customer an customer object
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    // -------------------------------------------------------------------------
    
    
    /**
     * Gets the user id
     * @return an int
     */
    public int getUserId() {
        return userId;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Sets the the user id
     * @param userId an int
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Gets the user as it related to this object
     * @return a user object
     */
    public User getUser() {
        return user;
    }

    // -------------------------------------------------------------------------
   
    /**
    *  Sets the user object of the object
    *  @param user an user object
    */
    public void setUser(User user) {
        this.user = user;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Gets the title of the appointment 
     * @return a string
     */
    public String getTitle() {
        return title;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Set the title of the appointment
     * @param title a string
     */
    public void setTitle(String title) {
        this.title = title;
    }

    // -------------------------------------------------------------------------
   
    /**
    *  Get the description of the appointment
    *  @return a string
    */
    public String getDescription() {
        return description;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Set the description of the appointment
     * @param description  a string
     */
    public void setDescription(String description) {
        this.description = description;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Gets the contact of the appointment
     * @return a string
     */
    public String getContact() {
        return contact;
    }

    // -------------------------------------------------------------------------
   /**
    * Set the contact of the appointment
    * @param contact a string
    */
    public void setContact(String contact) {
        this.contact = contact;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Gets the type of appointment
     * @return a string
     */
    public String getType() {
        return type;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Set the type of appointment
     * @param type a string
     */
    public void setType(String type) {
        this.type = type;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Gets the url for the appointment.
     * @return A string
     */
    public String getUrl() {
        return url;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Sets the url for the appointment
     * @param url a string
     */
    public void setUrl(String url) {
        this.url = url;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Gets the date/time of the start of the appointment
     * @return a Timestamp
     */
    public Timestamp getStart() {
        return start;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Sets the start time/date of the appointment
     * @param start a timestamp
     */
    public void setStart(Timestamp start) {
        this.start = start;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Gets the time/date of the end of the appointment
     * @return A timestamp
     */
    public Timestamp getEnd() {
        return end;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Sets the time/date of the end of an appointment
     * @param end a timestamp
     */
    public void setEnd(Timestamp end) {
        this.end = end;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Gets the time/date of when the object was created.
     * @return a timestamp
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Set the time/date of when the object was created.
     * @param createDate a timestamp
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Gets the user name that created the object.
     * @return a string
     */
    public String getCreatedBy() {
        return createdBy;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Sets the user name that created the object
     * @param createdBy a string
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Gets the time/date of the last time the object modified
     * @return a timestamp
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Set the time/date of the last time this object was modified
     * @param lastUpdate a timestamp
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Gets the last username that modified this object.
     * @return a string
     */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    // -------------------------------------------------------------------------
    
    /**
     * Set the username that last modified this object.
     * @param lastUpdateBy a string
     */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
    
    // -------------------------------------------------------------------------

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    
    
}  
