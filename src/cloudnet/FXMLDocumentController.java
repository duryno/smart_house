/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cloudnet;

import cloudnet.user.Login;
import cloudnet.user.SignUp;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

/**
 *
 * @author Juraj
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    public TextArea username;
    @FXML
    public TextArea password;
    @FXML
    private Button loginButton;
    @FXML
    private Label signUp;
    @FXML
    private Label login;
    @FXML
    private TextArea email;
    
    @FXML
    public void handleLogInButtonClick(MouseEvent e) {
        Login login = new Login();
        try {
            login.checkLogin(getUsername(), getPassword());
//            SignUp signUp = new SignUp();
//            signUp.createUser("email", getUsername(), getPassword());
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    public void handleSignUpClick(MouseEvent e) {
        signUp.setTextFill(Paint.valueOf("#2196f3"));
        login.setTextFill(Paint.valueOf("#ababab"));
        TranslateTransition moveTo = TranslateTransitionBuilder.
            create().
            toX(loginButton.getLayoutBounds().getMinX()).
            toY((loginButton.getLayoutBounds().getMinY()+85)-loginButton.getLayoutBounds().getMinY()).
            duration(Duration.seconds(0.5)).
            node(loginButton).
            build();
        moveTo.play(); 
        email.setVisible(true);
        email.setDisable(false);
    }
    
    @FXML
    public void handleLoginClick(MouseEvent e) {
        signUp.setTextFill(Paint.valueOf("#ababab"));
        login.setTextFill(Paint.valueOf("#2196f3"));
        TranslateTransition moveTo = TranslateTransitionBuilder.
            create().
            toX(loginButton.getLayoutBounds().getMinX()).
            toY((loginButton.getLayoutBounds().getMinY())-loginButton.getLayoutBounds().getMinY()).
            duration(Duration.seconds(0.5)).
            node(loginButton).
            build();
        moveTo.play(); 
        email.setVisible(false);
        email.setDisable(true);
    }
    
    private String getUsername() {
        return username.getText();
    }
    
    private String getPassword() {
        return password.getText();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
