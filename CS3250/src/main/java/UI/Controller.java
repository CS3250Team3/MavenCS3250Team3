package UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javafx.scene.control.Alert;
import CS3250.StringParsers;
import CS3250.DataMan;
import CS3250.User;
import CS3250.UserAuthenticator;
import CS3250.UserDB;

/**
 * Controls the login screen
 * 
 * @author Kyle Brown
 * 
 */
public class Controller {

    @FXML
    public Button exitBtn;

    @FXML
    public Button signBtn;

    @FXML
    public TextField user_IN;
    
    @FXML
    public PasswordField pass_IN;

    @FXML
    public String userName;

    @FXML
    public String passWord;

    /**
     * Retrieves inputted user name
     * @return - returns the entered username
     */
    @FXML
    public String get_User(){
        userName = user_IN.getText();
        return userName;
    }

    /**
     * Retrieves inputted password
     * @return - returns inputed password
     */
    @FXML
    public String get_Pass(){
        passWord = pass_IN.getText();
        return passWord;
    }

    /**
     * Attempts to authenticate the username and password entered
     * @param attemptedUser - Holds the entered username
     * @param attemptedPass - Holds the entered password
     * @return - returns result of the authetication
     * @throws NoSuchAlgorithmException - Throws if cryptograpic algorithm is requested but not available
     * @throws InvalidKeySpecException - Throws if invalid key specification
     */
    public boolean authenticated(String attemptedUser, String attemptedPass) throws NoSuchAlgorithmException, InvalidKeySpecException {
        DataMan<User> data = new UserDB();    	
        String dbConnection = StringParsers.readConfig("config");
        data.initializeDatabase(dbConnection);
    	
    	return UserAuthenticator.authenticate(attemptedUser, attemptedPass, data);
    }

    /**
     * Allows the window to be exited
     * @param event - clicking button event
     */
    @FXML
    public void exit_Button(ActionEvent event) {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }

    /**
     * Checks if user is authenticated, then loads db screen
     * @param event - clicking button event
     * @throws IOException - Throws if error with file
     * @throws NoSuchAlgorithmException - Throws if cryptograpic algorithm is requested but not available
     * @throws InvalidKeySpecException - Throws if invalid key specification
     */
    @FXML
    public void signIn_button(ActionEvent event) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    	String username = get_User();
    	String password = get_Pass();
        // Check if user is in database
    	if(authenticated(username,password)) {
            // Go to database inventory screen
    		Parent DbsScreen = FXMLLoader.load(getClass().getResource("totalView.fxml"));
            Scene DbsScene = new Scene(DbsScreen);
            Stage dbsStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            dbsStage.setScene(DbsScene);
            dbsStage.show();
    	}
    	else {
            // Error pop up for invalid credentials
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login ERROR");
            alert.setHeaderText("INCORRECT Username or Password");
            alert.showAndWait();
    	}
    }


    

}