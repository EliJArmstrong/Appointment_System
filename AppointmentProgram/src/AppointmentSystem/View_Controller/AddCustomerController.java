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
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import AppointmentSystem.Model.Customer;
import AppointmentSystem.Utilities.Query;

/**
 * FXML Controller class
 *
 * @author Eli Armstrong
 */
public class AddCustomerController implements Initializable {


    @FXML private TextField postField; 
    @FXML private TextField address2Field; 
    @FXML private TextField addressField; 
    @FXML private TextField nameField; 
    @FXML private ComboBox<String> cityComboBox; 
    @FXML private ComboBox<String> countryComboBox;
    @FXML private TextField phoneField; 
    Map<String, Integer> countryIdMap = new HashMap<>();
    Map<String, Integer> cityIdMap = new HashMap<>();
    
    
    // -------------------------------------------------------------------------
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setUpCountryComboBox();
        setUpPhoneField();
    }    
    
    // -------------------------------------------------------------------------
    
     /**
      * This method is called when the add button is pressed. When pressed the 
      * form fields are validated and if valid the customer is added to the server.
      * @param event The ActionEvent of the button.
      */
    @FXML void AddCustomerBtnPressed(ActionEvent event) {
        if(validateForm()){
            Customer customer = new Customer(this);
            customer.addCustomerToServer();
            toCustomerView();
        }  
        
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * This method is called when the cancel button is pressed. When this 
     * pressed the customer view is presented
     * @param event The ActionEvent of the button.
     */
    @FXML void cancelBtnPressed(ActionEvent event) {
        toCustomerView();
    }
    
    // -------------------------------------------------------------------------
        
        /**
         * Switches the stage to the customer view
         */
        private void toCustomerView(){
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
     * @param event he ActionEvent of Combo box.
     */
    @FXML void setUpCityCombox(ActionEvent event) {

        String selectedCountry = countryComboBox.getValue();
        cityComboBox.setPromptText("Select a City");
        cityComboBox.getItems().clear();
        cityIdMap.clear();
        
        String query = "SELECT city, cityId FROM city " +
                       "JOIN country WHERE city.countryId = country.countryId " 
                       + "AND country.countryId = " + 
                       String.valueOf(countryIdMap.get(selectedCountry)) + ";";
        
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
                presentAlert("Please " + tf.getPromptText(), tf);
            }
        } else{
            ComboBox cb = (ComboBox) field;
            if(cb.getValue() != null){
              isValid = true;
            } else{
                presentAlert("Please " + cb.getPromptText(), cb);
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
}
