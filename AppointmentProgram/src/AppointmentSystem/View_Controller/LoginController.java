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
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import AppointmentSystem.Utilities.Query;
import AppointmentSystem.Model.User;
import static AppointmentSystem.View_Controller.AppointmentSystem.getStage;
import static AppointmentSystem.View_Controller.AppointmentSystem.isConnectionMade;

/**
 *
 * @author Eli Armstrong
 * This class utilizes Internationalization
 */
public class LoginController implements Initializable {

    @FXML private TextField userNameField;     
    @FXML private PasswordField passwordField;
    @FXML private Label usernameLbl;
    @FXML private Label passwordLbl;
    @FXML private Button signInBtn;
    @FXML private Label titleLbl;
    
    // Holds the ResourceBundle for the languages
    ResourceBundle rb;
    
    // Holds the results set for the server
    ResultSet result;
    
    // -------------------------------------------------------------------------
    
    /**
     * Executes when the sign in button is pressed
     * @param event for the button press
     */
    @FXML private void signInButtonPressed(ActionEvent event) throws IOException {
        Logger log = Logger.getLogger("log.txt");
        FileHandler fileHandler = new FileHandler("log.txt", true);
        fileHandler.setFormatter(new SimpleFormatter());
        log.addHandler(fileHandler);
       try{
            // checks and Returns data from the server if the username and
            // password exist
            result = loginValidation(); 
            
            // If data exists in from the login fuction the else is executed.
            // else an error Alert is presented 
            if(!result.next()){
                log.log(Level.INFO, "Login: FAILED by Username tried: " + userNameField.getText() + " at " + LocalDateTime.now() + " " + TimeZone.getDefault().getID());
                LoginError();
            }else{
                
                // Sets the current user with the valid user login info.
                User.setCurrentUser(result);
                log.log(Level.INFO, "Login: SUCCESS by User: " + userNameField.getText() + " at " + LocalDateTime.now() + " " + TimeZone.getDefault().getID());
                
                // Go to Menu
                presentMenu();
                
            }
       } catch(SQLException ex){
           System.out.println("Oh No Somthing went wrong: " + ex.getLocalizedMessage());
       }
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * This method starts before the scene is launched to set up the components 
     * @param url
     * @param rb resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(!isConnectionMade()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Might not be connected to the internet", ButtonType.OK);
            alert.setHeaderText("Connection Error.");
            alert.setTitle("Connection Error");
            alert.showAndWait();
            getStage().close();
        }
        this.rb = rb;
        setupInternationalized();
    }    
    
    // -------------------------------------------------------------------------
    
    /**
     * Clears the username and password fields
     */
    private void clearFields(){
        userNameField.clear();
        passwordField.clear();
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Creates an alert for if a user enters an invalid username and password.
     * This function uses Internationalization.
     */
    private void LoginError(){
        Alert alert = new Alert(Alert.AlertType.WARNING, rb.getString("ErrorMessage"), ButtonType.OK);
        alert.setTitle(rb.getString("ErrorHeader"));
        alert.setHeaderText(rb.getString("ErrorHeader"));
         alert.showAndWait()
                 .filter(res -> res == ButtonType.APPLY);
         clearFields();
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * This method sets up the Internationalization for the scene.
     */
    private void setupInternationalized(){
        usernameLbl.setText(rb.getString("Username"));
        passwordLbl.setText(rb.getString("Password"));
        userNameField.setPromptText(rb.getString("UsernamePrompt"));
        passwordField.setPromptText(rb.getString("PasswordPrompt"));
        signInBtn.setText(rb.getString("SignIn"));
        titleLbl.setText(rb.getString("Scheduling"));
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Checks to see if the username and password entered is in the server. 
     * @return an ResultSet with one object is valid or an empty ResultSet if 
     * the username and password is not valid. 
     */
    private ResultSet loginValidation(){
        return Query.makeQuery("SELECT * FROM user WHERE userName = '"
               + userNameField.getText() + "' AND password = '" +
               passwordField.getText() + "'");
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Presents the main menu for the program.
     */
    private void presentMenu(){
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
    
}
