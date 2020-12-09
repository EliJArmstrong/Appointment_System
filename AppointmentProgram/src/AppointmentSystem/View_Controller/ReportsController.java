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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import AppointmentSystem.Model.Reports;
import AppointmentSystem.Utilities.Query;

/**
 * FXML Controller class
 *
 * @author Eli Armstrong
 */
public class ReportsController implements Initializable {

    @FXML private TableView<Reports> schedulesTableView;
    @FXML private TableColumn<Reports, String> scheduleDateCol;
    @FXML private TableColumn<Reports, String> scheduleAppointmentCol;
    @FXML private TableColumn<Reports, String> schedulesCustomerCol;
    
    @FXML private TableView<Reports> monthTableView;
    @FXML private TableColumn<Reports, String> monthTypeCol;
    @FXML private TableColumn<Reports, String> countCol;
    @FXML private TableColumn<Reports, String> monthCol;
    
    @FXML private TableView<Reports> consultantTableView;
    @FXML private TableColumn<Reports, String> consultNameCol;
    @FXML private TableColumn<Reports, String> consultCountCol;
    
    @FXML private ComboBox<String> userComboBox;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
    Map<String, Integer> userIdMap = new HashMap<>();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillMonthReport();
        filluserComboBox();
        fillConsultReport();
    }   
    
    // -------------------------------------------------------------------------
    
    /**
     * Move the view back to the main menu.
     * @param event ActionEvent for the button.
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
     * Fills the  monthTableView.
     */
    private void fillConsultReport(){
        
        ResultSet result = Query.makeQuery("SELECT userName, COUNT(*) as count FROM appointment, user WHERE appointment.userId = user.userId group by userName;");
        
        consultantTableView.getItems().clear();
        ObservableList<Reports> consultTypeList = FXCollections.observableArrayList();
        
        consultNameCol.setCellValueFactory(cellData -> {
                return new ReadOnlyStringWrapper(cellData.getValue().getUserName());
            });
        
        consultCountCol.setCellValueFactory(cellData -> {
                return new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getCount()));
            });
        
        try {
            while(result.next()){
                Reports report = new Reports();
                report.fillByConsultData(result);
                consultTypeList.add(report);  
            }
            consultantTableView.setItems(consultTypeList);
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Fills the  monthTableView.
     */
    private void fillMonthReport(){
        
        ResultSet result = Query.makeQuery("SELECT monthname(start) as month, type, COUNT(*) as count FROM appointment group by type, month;");
        
        monthTableView.getItems().clear();
        ObservableList<Reports> monthTypeList = FXCollections.observableArrayList();
        
        monthTypeCol.setCellValueFactory(cellData -> {
                return new ReadOnlyStringWrapper(cellData.getValue().getType());
            });
        
        countCol.setCellValueFactory(cellData -> {
                return new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getCount()));
            });
        
        monthCol.setCellValueFactory(cellData -> {
                return new ReadOnlyStringWrapper(String.valueOf(cellData.getValue().getMonth()));
            });
        
        try {
            while(result.next()){
                Reports report = new Reports();
                report.fillByTypeData(result);
                monthTypeList.add(report);  
            }
            monthTableView.setItems(monthTypeList);
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * When the combo box is changed this method is called.
     * @param event 
     */
    @FXML void userSelected(ActionEvent event) {
        
        ResultSet result = Query.makeQuery("SELECT customerName, type, start"
                + " FROM appointment, user, customer WHERE appointment.userId "
                + "= user.userId AND appointment.customerId = "
                + "customer.customerId AND appointment.userId = " + 
                userIdMap.get(userComboBox.getValue()) + " order by start;");
        
        schedulesTableView.getItems().clear();
        ObservableList<Reports> scheduleList = FXCollections.observableArrayList();
        
        scheduleDateCol.setCellValueFactory(cellData -> {
                return new ReadOnlyStringWrapper(dateFormatter.format(utcToLocal(cellData.getValue().getStart().toLocalDateTime())));
            });
        
        scheduleAppointmentCol.setCellValueFactory(cellData -> {
                return new ReadOnlyStringWrapper(cellData.getValue().getType());
            });
        
        schedulesCustomerCol.setCellValueFactory(cellData -> {
                return new ReadOnlyStringWrapper(cellData.getValue().getCustomerName());
            });
        
        try {
            while(result.next()){
                Reports report = new Reports();
                report.fillByScheduleData(result);
                scheduleList.add(report);  
            }
            schedulesTableView.setItems(scheduleList);
        } catch (SQLException ex) {
            Logger.getLogger(AppointmentsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * this will turn the server UTC time to the local date/time
     * @param utc a LocalDateTime of the server
     * @return a LocalDateTime
     */
    private LocalDateTime utcToLocal(LocalDateTime utc){
        ZonedDateTime utcZoneDataTime = ZonedDateTime.of(utc, ZoneId.of("UTC"));
        return utcZoneDataTime.withZoneSameInstant(ZoneOffset.systemDefault()).toLocalDateTime();
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
                userComboBox.getItems().add(userResult.getString("userName"));
            }
        } catch(SQLException ex){
            System.out.println(ex);
        }
    }
    
}
