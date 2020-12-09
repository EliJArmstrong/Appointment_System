/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppointmentSystem.View_Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import AppointmentSystem.Model.Appointment;
import AppointmentSystem.Utilities.Query;

/**
 * FXML Controller class
 *
 * @author Eli Armstrong
 */
public class AppointmentsController implements Initializable {

    @FXML private ComboBox<String> consultantComboBox;
    @FXML private ComboBox<String> timeComboBox;
    @FXML private DatePicker datePicker;  
    @FXML private ComboBox<String> typeComboBox;
    @FXML private ComboBox<String> customerComboBox;   
    Map<String, Integer> customerIdMap = new HashMap<>();
    Map<String, Integer> userIdMap = new HashMap<>();   
    @FXML private TableColumn<Appointment, String> monthConsultentCol;    
    @FXML private TableColumn<Appointment, String> allCustomerCol;
    @FXML private TableColumn<Appointment, String> monthDateTimeCol;
    @FXML private TableColumn<Appointment, String> allConsultentCol;
    @FXML private TableColumn<Appointment, String> allDateTimeCol;
    @FXML private TableColumn<Appointment, String> monthTypeCol;
    @FXML private TableColumn<Appointment, String> weekCustomerCol;
    @FXML private TableColumn<Appointment, String> allTypeCol;    
    @FXML private TableColumn<Appointment, String> weekDateTimeCol;
    @FXML private TableColumn<Appointment, String> weekTypeCol;   
    @FXML private TableColumn<Appointment, String> monthCustomerCol;
    @FXML private TableColumn<Appointment, String> weekConsultentCol;   
    @FXML private TableView<Appointment> nextMonthTableView;   
    @FXML private TableView<Appointment> allAppointmentsTableView;   
    @FXML private TableView<Appointment> nextWeekTableView;
    
    private int selectedRowIndex = 0;
    private TableView<Appointment> currentTableView = allAppointmentsTableView;


    private final String[] TIMES = 
    {"9:00 AM",   "9:15 AM",  "9:30 AM",  "9:45 AM",
     "10:00 AM", "10:15 AM", "10:30 AM", "10:45 AM",
     "11:00 AM", "11:15 AM", "11:30 AM", "11:45 AM",
     "12:00 PM", "12:15 PM", "12:30 PM", "12:45 PM",
     "1:00 PM",   "1:15 PM",  "1:30 PM",  "1:45 PM",
     "2:00 PM",   "2:15 PM",  "2:30 PM",  "2:45 PM",
     "3:00 PM",   "3:15 PM",  "3:30 PM",  "3:45 PM",
     "4:00 PM",   "4:15 PM",  "4:30 PM",  "4:45 PM",};
    
    private final String[] TYPES = {"Initial Meeting", "Follow-Up", "Interview"};
    
    // -------------------------------------------------------------------------
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillCustomerComboBox();
        filluserComboBox();
        setUpDatePicker();
        timeComboBox.getItems().addAll(TIMES);
        typeComboBox.getItems().addAll(TYPES);
        updateTableViews();
        allAppointmentsTableView.getSelectionModel().select(selectedRowIndex);
    }  
    
    // -------------------------------------------------------------------------
    
    /**
     * Updates all the table views from the server.  
     */
    private void updateTableViews(){
        loadAllTableView();
        loadMonthTableView();
        loadWeekTableView();
    }
    
    // -------------------------------------------------------------------------
    /**
     * When method called the month table view is loaded. 
     */
    private void loadMonthTableView(){
            
            nextMonthTableView.getItems().clear();
            ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        
            nextMonthTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    currentTableView = nextMonthTableView;
                    selectedRowIndex = nextMonthTableView.getSelectionModel().getSelectedIndex();
                    updateFieldsMonth();
                }
            });
      
            monthCustomerCol.setCellValueFactory(cellData -> {
                return new ReadOnlyStringWrapper(cellData.getValue().getCustomerName());
            });
        
            monthConsultentCol.setCellValueFactory(cellData -> {
                return new ReadOnlyStringWrapper(cellData.getValue().getUserName());
            });
        
            monthDateTimeCol.setCellValueFactory(cellData -> {
                return new ReadOnlyStringWrapper(cellData.getValue().getStart().toString());
            });
        
            monthTypeCol.setCellValueFactory(cellData -> {
                return new ReadOnlyStringWrapper(cellData.getValue().getType());
            });
        
            ResultSet result =  getMonthAppointments();
        
            try {
                while(result.next()){
                    appointmentList.add(new Appointment(result));  
                }
                nextMonthTableView.setItems(appointmentList);
            } catch (SQLException ex) {
                Logger.getLogger(AppointmentsController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
        
    // -------------------------------------------------------------------------
    
    /**
     * When method called the all table view is loaded. 
     */
    private void loadAllTableView(){
        
        allAppointmentsTableView.getItems().clear();
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        
        allAppointmentsTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                currentTableView = allAppointmentsTableView;
                selectedRowIndex = allAppointmentsTableView.getSelectionModel().getSelectedIndex();
                updateFieldsAll();
            }
        });
      
        allCustomerCol.setCellValueFactory(cellData -> {
            return new ReadOnlyStringWrapper(cellData.getValue().getCustomerName());
        });
        
        allConsultentCol.setCellValueFactory(cellData -> {
            return new ReadOnlyStringWrapper(cellData.getValue().getUserName());
        });
        
        allDateTimeCol.setCellValueFactory(cellData -> {
            return new ReadOnlyStringWrapper(cellData.getValue().getStart().toString());
        });
        
        allTypeCol.setCellValueFactory(cellData -> {
            return new ReadOnlyStringWrapper(cellData.getValue().getType());
        });
        
        ResultSet result = getAllAppointments();
        
        try {
            while(result.next()){
                appointmentList.add(new Appointment(result));  
            }
            allAppointmentsTableView.setItems(appointmentList);
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * When method called the week table view is loaded. 
     */
    private void loadWeekTableView(){
        
        nextWeekTableView.getItems().clear();
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        
        nextWeekTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                currentTableView = nextWeekTableView;
                selectedRowIndex = nextWeekTableView.getSelectionModel().getSelectedIndex();
                updateFieldsWeek();
            }
        });
      
        weekCustomerCol.setCellValueFactory(cellData -> {
            return new ReadOnlyStringWrapper(cellData.getValue().getCustomerName());
        });
        
        weekConsultentCol.setCellValueFactory(cellData -> {
            return new ReadOnlyStringWrapper(cellData.getValue().getUserName());
        });
        
        weekDateTimeCol.setCellValueFactory(cellData -> {
            return new ReadOnlyStringWrapper(cellData.getValue().getStart().toString());
        });
        
        weekTypeCol.setCellValueFactory(cellData -> {
            return new ReadOnlyStringWrapper(cellData.getValue().getType());
        });
        
        ResultSet result = getWeekAppointments();
        
        try {
            while(result.next()){
                appointmentList.add(new Appointment(result));  
            }
            nextWeekTableView.setItems(appointmentList);
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Update the fields when a row in the all appointments tab is selected
     */
    private void updateFieldsAll(){

        String timeString;

        consultantComboBox.getSelectionModel().select(allAppointmentsTableView.getSelectionModel().getSelectedItem().getUserName());
        customerComboBox.getSelectionModel().select(allAppointmentsTableView.getSelectionModel().getSelectedItem().getCustomerName());
        typeComboBox.getSelectionModel().select(allAppointmentsTableView.getSelectionModel().getSelectedItem().getType());
        datePicker.setValue(allAppointmentsTableView.getSelectionModel().getSelectedItem().getStart().toLocalDateTime().toLocalDate());
        LocalDateTime utcDateTime = allAppointmentsTableView.getSelectionModel().getSelectedItem().getStart().toLocalDateTime();
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
        timeComboBox.getSelectionModel().select(timeString);
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Updates the fields with the selected row from the month table view.
     */
    private void updateFieldsMonth(){
        
        String timeString;
        
        consultantComboBox.getSelectionModel().select(nextMonthTableView.getSelectionModel().getSelectedItem().getUserName());
        customerComboBox.getSelectionModel().select(nextMonthTableView.getSelectionModel().getSelectedItem().getCustomerName());
        typeComboBox.getSelectionModel().select(nextMonthTableView.getSelectionModel().getSelectedItem().getType());
        datePicker.setValue(nextMonthTableView.getSelectionModel().getSelectedItem().getStart().toLocalDateTime().toLocalDate());
        LocalDateTime utcDateTime = nextMonthTableView.getSelectionModel().getSelectedItem().getStart().toLocalDateTime();
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
        timeComboBox.getSelectionModel().select(timeString);
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Updates the fields with the selected row from the week table view.
     */
    private void updateFieldsWeek(){
        String timeString;
        
        consultantComboBox.getSelectionModel().select(nextWeekTableView.getSelectionModel().getSelectedItem().getUserName());
        customerComboBox.getSelectionModel().select(nextWeekTableView.getSelectionModel().getSelectedItem().getCustomerName());
        typeComboBox.getSelectionModel().select(nextWeekTableView.getSelectionModel().getSelectedItem().getType());
        datePicker.setValue(nextWeekTableView.getSelectionModel().getSelectedItem().getStart().toLocalDateTime().toLocalDate());
        LocalDateTime utcDateTime = nextWeekTableView.getSelectionModel().getSelectedItem().getStart().toLocalDateTime();
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
        timeComboBox.getSelectionModel().select(timeString);
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * When this method is called the customer id's and customer names are 
     * retrieved from the server and placed into the customer combo box. The 
     * customer name is then mapped to customer ID via being placed into the 
     * customerIdMap.
     */
    void fillCustomerComboBox(){
        
        // This gets a result set with the customers ID's and customer names' from the server.
        ResultSet customerResult = Query.makeQuery("SELECT customerId, customerName FROM customer;");
        
        try{
            while(customerResult.next()){
                customerIdMap.put(customerResult.getString("customerName"), customerResult.getInt("customerId"));
                customerComboBox.getItems().add(customerResult.getString("customerName"));
            }
        } catch(SQLException ex){
            System.out.println(ex);
        }
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * When this method is called the user id's and user names are retrieved 
     * from the server and placed into the consultant combo box. The user name 
     * is then mapped to users ID via being placed into the userIdMap.
     */
    void filluserComboBox(){
        
        // This gets a result set with the user ID's and username from the server.
        ResultSet userResult = Query.makeQuery("SELECT userId, userName FROM user;");

        try{
            while(userResult.next()){
                userIdMap.put(userResult.getString("userName"), userResult.getInt("userId"));
                consultantComboBox.getItems().add(userResult.getString("userName"));
            }
        } catch(SQLException ex){
            System.out.println(ex);
        }
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Sets up the date picker so that the weekends are disabled and date prior 
     * to now are also disable. The disable dates are in the color of gray.
     */
    void setUpDatePicker(){
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(
                    empty || 
                    date.getDayOfWeek() == DayOfWeek.SATURDAY || 
                    date.getDayOfWeek() == DayOfWeek.SUNDAY ||
                    date.isBefore(LocalDate.now()));
                if(date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                        date.getDayOfWeek() == DayOfWeek.SUNDAY ||
                        date.isBefore(LocalDate.now())) {
                    setStyle("-fx-background-color: #696969;");
                }
            }
        });
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * When this button is pressed the add appointment view is presented.
     * @param event the ActionEvent of button.
     */
    @FXML void addBtnPressed(ActionEvent event) {
        Parent main = null;
        try {
            main = FXMLLoader.load(getClass().getResource("AddAppointmentView.fxml"));
            Scene scene = new Scene(main);

            Stage stage = AppointmentSystem.getStage();
            
            stage.setScene(scene);
            stage.setTitle("Add Appointment");
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * This method is called when the back button is pressed. When the button is
     * pressed the main menu will be presented.
     * @param event the ActionEvent of button.
     */
    @FXML void backBtnPressed(ActionEvent event) {
        Parent main = null;
        try {
            main = FXMLLoader.load(getClass().getResource("Menu.fxml"));
            Scene scene = new Scene(main);

            Stage stage = AppointmentSystem.getStage();
            
            stage.setScene(scene);
            stage.setTitle("Main Menu");
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Saves edits and updates the appointment in the server/
     * @param event the ActionEvent of button.
     */
    @FXML void saveBtnPressed(ActionEvent event) {
        try {
            if(validateForm() && !appointmentOverLap()){
                currentTableView.getSelectionModel().getSelectedItem().updateThisAppointment(this);
                saveAlert("The appointment has been modified.");
                updateTableViews();
                currentTableView.getSelectionModel().select(0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * When this button is pressed the selected Appointment is delete from the 
     * server. After a confirmation alert.
     * @param event the ActionEvent of button.
     */
    @FXML void deleteBtnPressed(ActionEvent event) {
        deleteAlert("Are you sure you would like to remove the meeting between "
                + "customer: " + currentTableView.getSelectionModel().
                        getSelectedItem().getCustomerName() + " and consultent:"
                                + " " + 
                currentTableView.getSelectionModel().getSelectedItem()
                        .getUserName() + " at " + 
                currentTableView.getSelectionModel().getSelectedItem().
                        getStart().toString() + " (UTC) for a " + 
                currentTableView.getSelectionModel().getSelectedItem().getType());
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * This method checks to see if a Control has data.
     * @param field the control to check
     * @return true if the control has data in it else false.
     */
    private boolean isControlValid(Control field){
        
        boolean isValid = false;
        
        if(field instanceof TextField ){
            TextField tf = (TextField) field;
            if(!tf.getText().isEmpty()){
                isValid = true;
            } else{
                presentAlert("Please " + tf.getPromptText(), tf);
            }
        } else if (field instanceof ComboBox){
            ComboBox cb = (ComboBox) field;
            if(cb.getValue() != null){
              isValid = true;
            } else{
                presentAlert("Please " + cb.getPromptText(), cb);
            }
        } else if (field instanceof DatePicker){
            DatePicker dp = (DatePicker) field;
            if(dp.getValue() != null){
                isValid = true;
            }else{
                presentAlert("Please " + dp.getPromptText(), dp);
            }
        }
        return isValid;
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * This method presents an alert with a message and when the alert is closed 
     * the problem field is given focus
     * @param message a string to show the user
     * @param field a control to give focus too.
     */
    private void presentAlert(String message, Control field){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setHeaderText("Missing Field Information.");
        alert.setTitle(message);
        alert.showAndWait().filter(res -> res == ButtonType.OK).ifPresent(res -> field.requestFocus());
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Checks to see if the text fields and combo boxes are valid.
     * @return true is the text fields and combo boxes have info in them. 
     * Else false
     */
    boolean validateForm(){
        return (isControlValid(customerComboBox) && isControlValid(consultantComboBox) &&
                isControlValid(typeComboBox) && isControlValid(datePicker)
                && isControlValid(typeComboBox) && isControlValid(timeComboBox));
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Gets a result set of all appointments.
     * @return a ResultSet
     */
    private ResultSet getAllAppointments(){
        return Query.makeQuery("SELECT appointmentId, appointment.customerId, "
                + "appointment.userId, customerName, userName, type, start FROM"
                + " appointment, customer, user WHERE appointment.customerId ="
                + " customer.customerId AND appointment.userId = user.userId "
                + "order by start;");
    } 
    
    // -------------------------------------------------------------------------
    
    /**
     * Gets a result set of the next months appointments.
     * @return a ResultSet
     */
    private ResultSet getMonthAppointments(){
        return Query.makeQuery("SELECT appointmentId, appointment.customerId, "
                + "appointment.userId, customerName, userName, type, start FROM"
                + " appointment, customer, user WHERE appointment.customerId ="
                + " customer.customerId AND appointment.userId = user.userId AND"
                + " start > NOW() AND start < (NOW() + INTERVAL 31 DAY) "
                + "order by start;");
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Gets a result set of the next weeks appointments.
     * @return a ResultSet
     */
    private ResultSet getWeekAppointments(){
        return Query.makeQuery("SELECT appointmentId, appointment.customerId, "
                + "appointment.userId, customerName, userName, type, start FROM"
                + " appointment, customer, user WHERE appointment.customerId ="
                + " customer.customerId AND appointment.userId = user.userId AND"
                + " start > NOW() AND start < (NOW() + INTERVAL 8 DAY) "
                + "order by start;");
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Checks to see if there are overlapping appointments.
     * @return true if there is a over lapping appointment.
     */
    private boolean appointmentOverLap() throws SQLException{
        
        boolean returnValue = false;
        String customerNameAlready;
        
        ResultSet result = Query.makeQuery("SELECT *, customerName FROM "
                + "appointment, customer WHERE appointment.customerId = "
                + "customer.customerId AND userId = " + 
                userIdMap.get(consultantComboBox.getValue()) 
                + " AND start = '" + thisDateTimeToUTC().toString() + "';");
        
        if(result.next()){
            returnValue = true;
            customerNameAlready = result.getString("customerName");
            Alert alert = new Alert(Alert.AlertType.INFORMATION, 
                    consultantComboBox.getValue() + " has an appointment with "
                    + customerNameAlready + " at " + 
                    thisLocalDateTime().toString() + " already.", ButtonType.OK);
            alert.setHeaderText("Overlapping Appointment.");
            alert.setTitle("Appointment Error");
            alert.showAndWait();
        }

        return returnValue;
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * This method will take the time and date fields and returns a Timestamp 
     * of the chosen date/time in UTC time
     * @return A timestamp
     */
    private Timestamp thisDateTimeToUTC(){
        
        LocalDateTime formDateTime = thisLocalDateTime();
        ZonedDateTime localZoneDataTime = ZonedDateTime.of(formDateTime, ZoneId.systemDefault());
        ZonedDateTime UTCZoneDateTime = localZoneDataTime.withZoneSameInstant(ZoneOffset.UTC);
        return Timestamp.valueOf(UTCZoneDateTime.toLocalDateTime());   
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * returns the LocalDateTime for date and time fields.
     * @return a LocalDateTime
     */
    private LocalDateTime thisLocalDateTime(){
        String [] timeStringArray = timeComboBox.getValue().split(":| ");

        int hour = Integer.parseInt(timeStringArray[0]);
        int minute = Integer.parseInt(timeStringArray[1]);
        if(timeStringArray[2].equals("PM") && hour < 12){
            hour += 12;
        }
        
        return LocalDateTime.of(datePicker.getValue(), LocalTime.of(hour, minute));
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Gets the customer id from the customer Combo Box.
     * @return an int
     */
    public int getCustomerId(){
        return customerIdMap.get(customerComboBox.getValue());
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Gets the user id from the customer Combo Box.
     * @return an int
     */
    public int getUserId(){
        return userIdMap.get(consultantComboBox.getValue());
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Gets the meeting time of the .
     * @return an Timestamp
     */
    public Timestamp getDateTimeUTC(){
        return thisDateTimeToUTC();
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Gets the type of meeting
     * @return A string
     */
    public String getMeetingType(){
        return typeComboBox.getValue();
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Shows a message that tells the user a customer was added.
     * @param message the message to be displayed.
     */
    private void saveAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setHeaderText("Appointment Modified.");
        alert.setTitle("Appointment Modified");
        alert.showAndWait();
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Shows an alert to make sure the user wants to delete the customer from 
     * the server.
     * @param message a string to be shown to the user.
     */
    private void deleteAlert(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Appointment?");
        alert.showAndWait().ifPresent((response -> {
            if(response == ButtonType.OK) {
                  currentTableView.getSelectionModel().getSelectedItem().deleteThisObject();
                  updateTableViews();
                  currentTableView.getSelectionModel().select(0);
            }
        }));
    }
}
