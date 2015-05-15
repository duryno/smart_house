/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudnet;

import cloudnet.user.Login;
import cloudnet.user.UserDropBox;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Juraj
 */

public class FXMLDocumentController implements Initializable {

    @FXML
    public static TextField username;
    @FXML
    public static PasswordField password;
    @FXML
    private Button loginButton;
    @FXML
    private Label signUp;
    @FXML
    private Label login;
    @FXML
    private TextField email; 
    @FXML
    Button button, activateButton;
    @FXML
    AnchorPane content;
    @FXML
    AnchorPane anc;
    @FXML
    TextField tokenTextField;
    @FXML
    ListView list, fileList;
    @FXML
    static Label warning;

    CloudNet cloud = new CloudNet();
    final String keyForApp = "3arl279eij5125u";
    final String secretKeyForApp = "ic83wodtpty04ut";
    private DbxClient dd;
    static String accessToken;

    static final String RED_BORDER = "-fx-border-color: rgb(255, 0, 0);";
    static final String EMPTY_FIELD = "No field can be empty!";
    static final String USER_HOME_SCREEN = "userHome.fxml";
    static final String NO_ACCOUNTS_SCREEN = "noAccountsScreen.fxml";
    static final String NO_USER = "No user found / password incorrect";
    
    private enum direction {UP, DOWN};

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    
    @FXML
    public void handleLogInButtonAction() {       
        boolean correct = false;
        Login login = new Login();
        try {
            if(loginButton.getText().equals("Log in")) {     
                correct = login.checkLogin(getUsername(), getPassword());
                if(correct == true)
                    cloud.goToNextScreen(NO_ACCOUNTS_SCREEN);
            } else {
                correct = login.createUser(getEmail(), getUsername(), getPassword());
                if(correct == true)
                    cloud.goToNextScreen(NO_ACCOUNTS_SCREEN);
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void handleSignUpClick(MouseEvent e) {
        signUp.setTextFill(Paint.valueOf("#2196f3"));
        login.setTextFill(Paint.valueOf("#ababab"));
        moveNode(loginButton, direction.DOWN);
        moveNode(warning, direction.DOWN);
        email.setVisible(true);
        email.setDisable(false);
        loginButton.setText("Sign up");
    }

    @FXML
    public void handleLoginClick(MouseEvent e) {
        signUp.setTextFill(Paint.valueOf("#ababab"));
        login.setTextFill(Paint.valueOf("#2196f3"));
        moveNode(loginButton, direction.UP);
        moveNode(warning, direction.UP);
        email.setVisible(false);
        email.setDisable(true);
        loginButton.setText("Log in");
    }

    private void moveNode(Node node, direction dir) {
        double y;
        if(dir.equals(direction.UP))
            y = (node.getLayoutBounds().getMinY())-node.getLayoutBounds().getMinY();
        else
            y = (node.getLayoutBounds().getMinY()+85)-node.getLayoutBounds().getMinY();

        TranslateTransition moveTo = TranslateTransitionBuilder.
                create().
                toY(y).
                duration(Duration.seconds(0.5)).
                node(node).
                build();
        moveTo.play();
    }

    private String getUsername() {
        String text = username.getText();
        if(text.isEmpty()) {
            warning.setText(EMPTY_FIELD);
            username.setStyle(RED_BORDER);
        }
        return username.getText();
    }

    private String getPassword() {
        String text = password.getText();
        if(text.isEmpty()) {
            warning.setText(EMPTY_FIELD);
            password.setStyle(RED_BORDER);
        } else if(loginButton.getText().equals("Sign up") && text.length() < 6) {
            warning.setText("Password is too short");
            return "";
        }
        return password.getText();
    }

    private String getEmail() {
        String text = email.getText();
        if(text.isEmpty()) {
            warning.setText(EMPTY_FIELD);
            email.setStyle(RED_BORDER);
        } else if(!text.contains("@") || !text.contains(".")) {
            warning.setText("Invalid e-mail address");
            return "";
        }
        return email.getText();
    }
    
     public void handleNoUser() {
        warning.setText(NO_USER);
        username.setStyle(RED_BORDER);
        password.setStyle(RED_BORDER);
    }
    
}
