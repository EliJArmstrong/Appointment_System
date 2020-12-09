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
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import AppointmentSystem.Model.Customer;
import AppointmentSystem.Utilities.Query;

/**
 * FXML Controller class
 *
 * @author Eli Armstrong
 */
public class CustomerController implements Initializable {

    @FXML private TextField postField; 
    @FXML private TextField address2Field; 
    @FXML private TextField addressField; 
    @FXML private TextField nameField; 
    @FXML private ComboBox<String> cityComboBox; 
    @FXML private ComboBox<String> countryComboBox;
    @FXML private TextField phoneField; 
    @FXML private Button saveBtn;
    @FXML private Button deleteBtn;
    Map<String, Integer> countryIdMap = new HashMap<>();
    Map<String, Integer> cityIdMap = new HashMap<>();
    @FXML private TableView<Customer> customerNameTV;
    @FXML private TableColumn<Customer, String> customerCol;
    
    // -------------------------------------------------------------------------
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {     
        updateView(0);
        setUpPhoneField();
    }
    
    
    // -------------------------------------------------------------------------
    
    
    /**
     * Disables all controls
     */
    void disableAllControls(){
        postField.setDisable(true); 
        address2Field.setDisable(true); 
        addressField.setDisable(true); 
        nameField.setDisable(true); 
        cityComboBox.setDisable(true); 
        countryComboBox.setDisable(true);
        phoneField.setDisable(true);
        saveBtn.setDisable(true);
        deleteBtn.setDisable(true);
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Enables all controls
     */
    void enableAllConretols(){
        postField.setDisable(false); 
        address2Field.setDisable(false); 
        addressField.setDisable(false); 
        nameField.setDisable(false); 
        cityComboBox.setDisable(false); 
        countryComboBox.setDisable(false);
        phoneField.setDisable(false);
        saveBtn.setDisable(false);
        deleteBtn.setDisable(false);
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Updates the fields based on the row in the table view that is selected.
     */
    void updateFields(){
        nameField.setText(customerNameTV.getSelectionModel().getSelectedItem().getCustomerName());
        addressField.setText(customerNameTV.getSelectionModel().getSelectedItem().getAddress().getAddress());
        address2Field.setText(customerNameTV.getSelectionModel().getSelectedItem().getAddress().getAddress2());
        postField.setText(customerNameTV.getSelectionModel().getSelectedItem().getAddress().getPostalCode());
        phoneField.setText(customerNameTV.getSelectionModel().getSelectedItem().getAddress().getPhone());
        countryComboBox.getSelectionModel().select(customerNameTV.getSelectionModel().getSelectedItem().getAddress().getCity().getCountry().getCountryName());
        setUpCityCombox();
        cityComboBox.getSelectionModel().select(customerNameTV.getSelectionModel().getSelectedItem().getAddress().getCity().getCityName());
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Sets up the Country Combo Box 
     */
    void setUpCountryComboBox(){
        
        // Grabs the country name and country ID for the server.
        ResultSet result = Query.makeQuery("SELECT country, countryId FROM country");
        
        try{
            while(result.next()){
                int id = result.getInt("countryId");
                String countryName = result.getString("country");
                countryIdMap.put(countryName, id);
                countryComboBox.getItems().add(countryName);
            }
        } catch(SQLException ex){
            System.out.println(ex);
        }
        
    }
    
    
    // -------------------------------------------------------------------------
    
    /**
     * This method is called when a country is selected in the Country Combo Box 
     * @param event the ActionEvent of Combo box.
     */
    @FXML void setUpCityCombox() {

        String selectedCountry = countryComboBox.getValue();
        cityComboBox.setPromptText("Select a City");
        cityComboBox.getItems().clear();
        cityIdMap.clear();
        
        String query = "SELECT city, cityId FROM city " +
                       "JOIN country WHERE city.countryId = country.countryId " 
                       + "AND country.countryId = " + 
                       countryIdMap.get(selectedCountry) + ";";
        
        ResultSet result = Query.makeQuery(query);
        
        try{
            while(result.next()){
                int id = result.getInt("cityId");
                String cityName = result.getString("city");
                cityIdMap.put(cityName, id);
                cityComboBox.getItems().add(cityName);
            }
        } catch(SQLException ex){
            System.out.println(ex);
        }
        
        cityComboBox.setDisable(false);
        
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Called when the Add a Customer button is pressed.
     * When this button is pressed the add customer view is presented.
     * @param event the ActionEvent of button.
     */
    @FXML void addCustomerBtnPressed(ActionEvent event) {
        Parent main = null;
        try {
            main = FXMLLoader.load(getClass().getResource("AddCustomerView.fxml"));
            Scene scene = new Scene(main);

            Stage stage = AppointmentSystem.getStage();
            
            stage.setScene(scene);
            stage.setTitle("Add Customer");
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
     * This method is called when the save button is pressed. When this button 
     * is pressed the currently highlighted customer in the table view is 
     * updated to with the information in the fields. 
     * @param event the ActionEvent of button.
     */
    @FXML void saveBtnPressed(ActionEvent event) {
        if(validateForm()){
            customerNameTV.getSelectionModel().getSelectedItem().updateCustomerToServer(this);
            saveAlert("Customer " + customerNameTV.getSelectionModel().getSelectedItem().getCustomerName() + " has been modified.");
            updateView(customerNameTV.getSelectionModel().getSelectedIndex());
        }
    }
    
    // ------------------------------------------------------------------------
    
    /**
     * This method is called when the delete button is pressed. When pressed 
     * the highlighted customer is remove from the server.
     * @param event 
     */
    @FXML void deleteBtnPressed(ActionEvent event){
        deleteAlert("Are you sure Customer: " + customerNameTV.getSelectionModel().getSelectedItem().getCustomerName() + "?");
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Checks to see if the text fields and combo boxes are valid.
     * @return true is the text fields and combo boxes have info in them. 
     * Else false
     */
    boolean validateForm(){
        return (isControlValid(nameField) && isControlValid(addressField) &&
                isControlValid(countryComboBox) && isControlValid(cityComboBox)
                && isControlValid(postField) && isControlValid(phoneField));
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
                presentControlAlert("Please " + tf.getPromptText(), tf);
            }
        } else{
            ComboBox cb = (ComboBox) field;
            if(cb.getValue() != null){
              isValid = true;
            } else{
                presentControlAlert("Please " + cb.getPromptText(), cb);
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
    private void presentControlAlert(String message, Control field){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setHeaderText("Missing Field Information.");
        alert.setTitle(message);
        alert.showAndWait().filter(res -> res == ButtonType.OK).ifPresent(res -> field.requestFocus());
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Shows a message that tells the user a customer was added.
     * @param message the message to be displayed.
     */
    private void saveAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setHeaderText("Customer Modified.");
        alert.setTitle("Customer Modified");
        alert.showAndWait();
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Shows an alert to make sure the user wants to delete the customer from 
     * the server.
     * @param message a string to be shown to the user.
     */
    private void deleteAlert(String message){
        Alert alert = new Alert(AlertType.CONFIRMATION, message);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Customer?");
        alert.showAndWait().ifPresent((response -> {
            if(response == ButtonType.OK) {
                  customerNameTV.getSelectionModel().getSelectedItem().deleteCustomerFromServer();
                  updateView(0);
            }
        }));
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * This updates the view when information is loaded or change.
     * @param index the row in the table view to be selected.
     */
    void updateView(int index){
        disableAllControls();
        setUpCountryComboBox();
        customerNameTV.getItems().clear();
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        
        // The lambda adds an easier way to add a listener in order to perform an action when a row is selected in the customer table view.
        customerNameTV.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateFields();
            }
        });
        
        // This lambda expression made it much easier to set the columns in the table view by passing an object instead of a primitve data type.
        customerCol.setCellValueFactory(cellData -> {
            return new ReadOnlyStringWrapper(cellData.getValue().getCustomerName());
        });
        
        try{
            ResultSet result = getCustomersFromSever();
        
            while(result.next()){
                customerList.add(new Customer(result));
            }
            
            customerNameTV.setItems(customerList);
            customerNameTV.getSelectionModel().select(index);
            if(!customerList.isEmpty()){
                enableAllConretols();
            }
        } catch(SQLException ex){
            System.out.println(ex);
        }
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Makes it so only number can be added to the phone number field.
     */
    private void setUpPhoneField(){
        phoneField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                phoneField.setText(newValue.replaceAll("\\D", ""));
            }
        });
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * get the text from the post field
     * @return a string
     */
    public String getPostCode(){
        return postField.getText();
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Gets the text from address field
     * @return a string
     */
    public String getAddress(){
        return addressField.getText();
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Gets the text from address 2 field
     * @return a string
     */
    public String getAddress2(){
        return address2Field.getText();
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Gets the text from name field
     * @return a string
     */
    public String getCustomerName(){
        return nameField.getText();
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * gets the Country Code
     * @return an int
     */
    public int getCountryCode(){
        return countryIdMap.get(countryComboBox.getValue());
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * gets the City Code
     * @return an int
     */
    public int getCityCode(){
        return cityIdMap.get(cityComboBox.getValue());
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Get the text for the phone field
     * @return a string
     */
    public String getPhoneNumber(){
        return phoneField.getText();
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * A query to get customer information from the server.
     * @return A result set 
     */
    private ResultSet getCustomersFromSever(){
        return Query.makeQuery("SELECT customerId, customerName, "
                + "address.addressId, address, address2, city.cityId, "
                + "postalCode, phone, city, country.countryId, country  "
                + "FROM customer, address, city, country WHERE "
                + "customer.addressId = address.addressId AND address.cityId = "
                + "city.cityId AND city.countryId = country.countryId order by "
                + "customerId;");
    }

}
