/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppointmentSystem.View_Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import AppointmentSystem.Utilities.JDBConnection;


/**
 *
 * @author Eli Armstrong
 */
public class AppointmentSystem extends Application {
    
    // Stage to be handed off to other scenes.
    static Stage stage;
    
    // connection made flag to check if a connection was made
    static boolean connectionMade = false; 
    
    // -------------------------------------------------------------------------
    
    /**
     * 
     * @param stage
     * @throws Exception 
     */
    @Override
    public void start(Stage stage) throws Exception {
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // Uncomment the line below to set the default to Japan
        // Locale.setDefault(new Locale("ja", "JP"));
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        ResourceBundle rb = ResourceBundle.getBundle("lang_files/lang");
        
        
        Parent root = null;
        try{
            
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
        loader.setResources(rb);
        root = loader.load();
        
        Scene scene = new Scene(root);
        
        this.stage = stage;
        stage.setScene(scene);
        stage.setTitle(rb.getString("Login"));
        stage.show();
        
        } catch(IOException ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }

    // -------------------------------------------------------------------------
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){

        try {
            JDBConnection.makeConnection();
            connectionMade = true;
            launch(args);
            JDBConnection.closeConnection();
         } catch (Exception ex) {
           System.out.println("Oh No an exception was made. Here are the details: " + ex.getMessage());
           launch(args);
            try {
                JDBConnection.closeConnection();
            } catch (SQLException ex1) {
                Logger.getLogger(AppointmentSystem.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (Exception ex1) {
                Logger.getLogger(AppointmentSystem.class.getName()).log(Level.SEVERE, null, ex1);
            }
         }
    }
    
    // -------------------------------------------------------------------------
    
    /**
     * Get the stage for the program to use.
     * @return the main stage for the program
     */
    static Stage getStage() {
        return stage;
    }
    
    // -------------------------------------------------------------------------

    public static boolean isConnectionMade() {
        return connectionMade;
    }
    
    
} 
    

