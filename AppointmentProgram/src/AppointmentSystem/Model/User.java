/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppointmentSystem.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


/***
 *     _   _                 _____ _               
 *    | | | |               /  __ \ |              
 *    | | | |___  ___ _ __  | /  \/ | __ _ ___ ___ 
 *    | | | / __|/ _ \ '__| | |   | |/ _` / __/ __|
 *    | |_| \__ \  __/ |    | \__/\ | (_| \__ \__ \
 *     \___/|___/\___|_|     \____/_|\__,_|___/___/
 *                                                 
 *
 */


/**
 *
 * @author Eli Armstrong
 * This class holds the the values of a user for the mySQL sever.
 */
public class User {
    
    
/***
 *     _   _                 _____          _                       
 *    | | | |               |_   _|        | |                      
 *    | | | |___  ___ _ __    | | _ __  ___| |_ __ _ _ __   ___ ___ 
 *    | | | / __|/ _ \ '__|   | || '_ \/ __| __/ _` | '_ \ / __/ _ \
 *    | |_| \__ \  __/ |     _| || | | \__ \ || (_| | | | | (_|  __/
 *     \___/|___/\___|_|     \___/_| |_|___/\__\__,_|_| |_|\___\___|
 *                                                                  
 *                                                                  
 */
    // This holds the information of the current logged in user.
    public static User currentUser;
    
/***
 *      _    _                __      __        _       _     _           
 *     | |  | |               \ \    / /       (_)     | |   | |          
 *     | |  | |___  ___ _ __   \ \  / /_ _ _ __ _  __ _| |__ | | ___  ___ 
 *     | |  | / __|/ _ \ '__|   \ \/ / _` | '__| |/ _` | '_ \| |/ _ \/ __|
 *     | |__| \__ \  __/ |       \  / (_| | |  | | (_| | |_) | |  __/\__ \
 *      \____/|___/\___|_|        \/ \__,_|_|  |_|\__,_|_.__/|_|\___||___/
 *                                                                        
 *                                                                        
 */

    private int userId;           // Holds the user ID
    private String userName;      // Holds the username
    private int active;           // Holds of the user is active
    private Timestamp createDate; // The date the user was created
    private String cratedBy;      // Who created the user
    private Timestamp lastUpdate; // The date the user was modified
    private String lastUpdateBy;  // Who modified the user infomation
    
    
/***
 *     _   _                ___  ___     _   _               _     
 *    | | | |               |  \/  |    | | | |             | |    
 *    | | | |___  ___ _ __  | .  . | ___| |_| |__   ___   __| |___ 
 *    | | | / __|/ _ \ '__| | |\/| |/ _ \ __| '_ \ / _ \ / _` / __|
 *    | |_| \__ \  __/ |    | |  | |  __/ |_| | | | (_) | (_| \__ \
 *     \___/|___/\___|_|    \_|  |_/\___|\__|_| |_|\___/ \__,_|___/
 *                                                                 
 *                                                                 
 */
    
    /**
    * This method does takes in a ResultSet from the mySQL sever and fills the
    * user's values.
    * @param result The ResultSet to get data from 
    * @throws java.sql.SQLException 
    */
    public void setUser(ResultSet result) throws SQLException{
        
        userName     = result.getString("userName");
        userId       = result.getInt("userId");
        active       = result.getInt("active");
        createDate   = result.getTimestamp("createDate");
        cratedBy     = result.getString("createdBy");
        lastUpdate   = result.getTimestamp("lastUpdate");
        lastUpdateBy = result.getString("lastUpdateBy");
    }
    
    // -------------------------------------------------------------------------
    
    /**
    * This method does takes in a ResultSet from the mySQL sever and fills the
    * static current user's values.
    * @param result The ResultSet to get data from 
    * @throws java.sql.SQLException 
    */
    public static void setCurrentUser(ResultSet result) throws SQLException{
        
        if(User.currentUser == null){
            User.currentUser = new User();
        }
        
        User.currentUser.userName     = result.getString("userName");
        User.currentUser.userId       = result.getInt("userId");
        User.currentUser.active       = result.getInt("active");
        User.currentUser.createDate   = result.getTimestamp("createDate");
        User.currentUser.cratedBy     = result.getString("createdBy");
        User.currentUser.lastUpdate   = result.getTimestamp("lastUpdate");
        User.currentUser.lastUpdateBy = result.getString("lastUpdateBy");
    }
    
    // -------------------------------------------------------------------------
    
    /**
    * This method returns a string with the users ID and Username
    * @return a string with user id and username
    */
    @Override
    public String toString(){
        return "user ID: " + String.valueOf(userId) + " Username: " + userName;
    }

    // -------------------------------------------------------------------------
    
    /**
    * This method returns a string with the Username variable
    * @return a string with the user's username
    */
    public String getUserName() {
        return userName;
    }

    // -------------------------------------------------------------------------
    
    /**
    * This method sets the class Username variable
    * @param userName the username to be set.
    */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    // -------------------------------------------------------------------------
    
    /**
    * This method returns a string with the UserId variable
    * @return a string with the user's UserId
    */
    public int getUserId() {
        return userId;
    }

    // -------------------------------------------------------------------------
    
    /**
    * This method sets the class UserId variable
    * @param userId the user id to be set.
    */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    // -------------------------------------------------------------------------
    
    /**
    * This method returns a int with the Active state variable
    * @return a int with the user's Active state
    */
    public int getActive() {
        return active;
    }

    // -------------------------------------------------------------------------
    
    /**
    * This method sets the class active variable
    * @param active the active state to be set.
    */
    public void setActive(int active) {
        this.active = active;
    }

    // -------------------------------------------------------------------------
    
    /**
    * This method returns a Date with the CreateDate variable
    * @return a Date with the user's CreateDate
    */
    public Timestamp getCreateDate() {
        return createDate;
    }

    // -------------------------------------------------------------------------
    
    /**
    * This method sets the class createDate variable
    * @param createDate the date to be set.
    */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    // -------------------------------------------------------------------------
    
    /**
    * This method returns a string with the CratedBy variable
    * @return a string with who created the user
    */
    public String getCratedBy() {
        return cratedBy;
    }

    // -------------------------------------------------------------------------
    
    /**
    * This method sets the class cratedBy variable
    * @param cratedBy the string to be set.
    */
    public void setCratedBy(String cratedBy) {
        this.cratedBy = cratedBy;
    }

    // -------------------------------------------------------------------------
    
    /**
    * This method returns a string with the CratedBy variable
    * @return a string with who created the user
    */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    // -------------------------------------------------------------------------
    
    /**
    * This method sets the class lastUpdate variable
    * @param lastUpdate the date to be set.
    */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    // -------------------------------------------------------------------------
    
    /**
    * This method returns a string with the LastUpdateBy variable
    * @return a string with who changed the user
    */
    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    // -------------------------------------------------------------------------
    
    /**
    * This method sets the class LastUpdateBy variable
    * @param lastUpdateBy the string to be set.
    */
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
}
