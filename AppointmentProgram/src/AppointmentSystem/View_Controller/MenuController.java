/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppointmentSystem.View_Controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import AppointmentSystem.Model.User;
import AppointmentSystem.Utilities.Query;

/**
 * FXML Controller class
 *
 * @author Eli Armstrong
 */
public class MenuController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override public void initialize(URL url, ResourceBundle rb) {
        checkForAppointmentsWithin15Minutes();
    }  
    
    // -------------------------------------------------------------------------
    
    /**
     * This method checks to see if the user that logged in has a appointment 
     * with in 15 minutes.
     */
    private void checkForAppointmentsWithin15Minutes(){
        ResultSet result = Query.makeQuery("SELECT *, customerName FROM "
                + "appointment, customer WHERE appointment.customerId = "
                + "customer.customerId AND start >= NOW() AND start <= "
                + "DATE_ADD(NOW(),  INTERVAL 15 MINUTE) AND userId = " + 
                User.currentUser.getUserId() + " order by start;");
        
        try {
            if(result.next()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, 
                        "You have an " + result.getString("type") + " with " +
                                result.getString("customerName") + " at " +
                                UTCToLocalNormalTime(
                                result.getTimestamp("start")) + ".", 
                                ButtonType.OK);
                alert.setHeaderText("Up comming appointment.");
                alert.setTitle("Up comming appointment");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // -------------------------------------------------------------------------
        
        /**
         * Turns the Timestamp from the server into the local time of the user.
         * In the format HH:MM AM.
         * @param dateTime the dateTime to be converted
         * @return a string in the format of HH:MM AM/PM
         */
        private String UTCToLocalNormalTime(Timestamp dateTime){
        
            String timeString;
            
            LocalDateTime utcDateTime = dateTime.toLocalDateTime();
            ZonedDateTime utcZoneDataTime = ZonedDateTime.of(utcDateTime, ZoneId.of("UTC"));
            ZonedDateTime localZoneDateTime = utcZoneDataTime.withZoneSameInstant(ZoneOffset.systemDefault());
            int hour = localZoneDateTime.toLocalTime().getHour();
            int minute = localZoneDateTime.toLocalTime().getMinute();
        
            if(hour < 12){
                timeString = hour + ":" + minute + ((minute == 0) ? "0" : "") +  " AM"; 
            } else{
                if(hour == 12){
                    timeString = hour + ":" + minute + ((minute == 0) ? "0" : "") + " PM";
                } else{
                    hour -= 12;
                    timeString = hour + ":" + minute + ((minute == 0) ? "0" : "") + " PM";
                }
            }
            
            return timeString;
        }
    // -------------------------------------------------------------------------
    
    /**
     * The method is called when the appointment button is pressed. After this 
     * button is pressed the appointment view is presented.
     * @param event The Action event for the button press.
     */
    @FXML void appointmentsBtnPressed(ActionEvent event) {
        toAppointmentView();
    }
    
    // -------------------------------------------------------------------------
    
     /**
      * This method is called when the customer button is pressed. When this 
      * button is pressed then the stage to changed to the customer view.
      * @param event The Action event for the button press.
      */
    @FXML void customerBtnPressed(ActionEvent event) {
        toCustomerView();
    }
    
    // -------------------------------------------------------------------------
    
    @FXML void reportBtnPressed(ActionEvent event) {
        toReportsView();
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * This method is called when the user presses the logout button and the 
     * current user is set to null 
     * @param event The Action event for the button press.
     */
    @FXML void logoutPressed(ActionEvent event) {
        User.currentUser = null;
        toLoginView();
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * When this method is called the login view is presented.
     */
    void toLoginView(){
        
        Parent main = null;
        ResourceBundle rb = ResourceBundle.getBundle("lang_files/lang");
        
        try {
             
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
            loader.setResources(rb);
            main = loader.load();
        
            Scene scene = new Scene(main);
        
            Stage stage = AppointmentSystem.getStage();
            stage.setScene(scene);
            stage.setTitle(rb.getString("Login"));
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * When this method is called the stage elements to the customer view. 
     */
    void toCustomerView(){
        Parent main = null;
        try {
            main = FXMLLoader.load(getClass().getResource("CustomerView.fxml"));
            Scene scene = new Scene(main);

            Stage stage = AppointmentSystem.getStage();
            
            stage.setScene(scene);
            stage.setTitle("Customer");
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * When this method is called the stage elements to the appointment view. 
     */
    void toAppointmentView(){
        Parent main = null;
        try {
            main = FXMLLoader.load(getClass().getResource("AppointmentsView.fxml"));
            Scene scene = new Scene(main);

            Stage stage = AppointmentSystem.getStage();
            
            stage.setScene(scene);
            stage.setTitle("Appointments");
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * When this method is called the stage elements to the appointment view. 
     */
    void toReportsView(){
        Parent main = null;
        try {
            main = FXMLLoader.load(getClass().getResource("ReportsView.fxml"));
            Scene scene = new Scene(main);

            Stage stage = AppointmentSystem.getStage();
            
            stage.setScene(scene);
            stage.setTitle("Reports");
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    // -------------------------------------------------------------------------
    
    /**
     * @param event The Action event for the button press. This grabs and shows 
     * the log file.
     */
    @FXML void logBtnPressed(ActionEvent event) {
        File file = new File("log.txt");
        if(file.exists() && Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }
    
}
