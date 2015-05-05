/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudnet;

import cloudnet.user.Login;
import cloudnet.user.SignUp;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Juraj
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    public TextArea username;
    @FXML
    public PasswordField password;
    @FXML
    private Button loginButton;
    @FXML
    private Label signUp;
    @FXML
    private Label login;
    @FXML
    private TextArea email;
    
    CloudNet cloud = new CloudNet();

    @FXML
    public void handleLogInButtonClick(MouseEvent e) throws IOException {
        
        cloud.goToNextScreenTest();
        
        boolean correct = false;
        try {
            if (loginButton.getText().equals("Log in")) {
                Login login = new Login();
                correct = login.checkLogin(getUsername(), getPassword());
            } else {
                SignUp signUp = new SignUp();
                correct = signUp.createUser(getEmail(), getUsername(), getPassword());
            }

            if (correct == true) {
                cloud.goToNextScreenTest();
            }

        } catch (Exception ex) {
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
                toY((loginButton.getLayoutBounds().getMinY() + 85) - loginButton.getLayoutBounds().getMinY()).
                duration(Duration.seconds(0.5)).
                node(loginButton).
                build();
        moveTo.play();
        email.setVisible(true);
        email.setDisable(false);
        loginButton.setText("Sign up");
    }

    @FXML
    public void handleLoginClick(MouseEvent e) {
        signUp.setTextFill(Paint.valueOf("#ababab"));
        login.setTextFill(Paint.valueOf("#2196f3"));
        TranslateTransition moveTo = TranslateTransitionBuilder.
                create().
                toX(loginButton.getLayoutBounds().getMinX()).
                toY((loginButton.getLayoutBounds().getMinY()) - loginButton.getLayoutBounds().getMinY()).
                duration(Duration.seconds(0.5)).
                node(loginButton).
                build();
        moveTo.play();
        email.setVisible(false);
        email.setDisable(true);
        loginButton.setText("Log in");
    }

    private String getUsername() {
        return username.getText();
    }

    private String getPassword() {
        return password.getText();
    }

    private String getEmail() {
        return email.getText();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
