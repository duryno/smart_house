/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cloudnet;

import static cloudnet.CloudNet.homeControl;
import static cloudnet.CloudNet.stagey;
import cloudnet.user.UserDropBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Juraj
 */
public class NoAccountsScreenController implements Initializable {
    
    @FXML
    Button logout;
    @FXML
    Button circleDropbox;
    @FXML
    Button circleGoogle;
    @FXML
    Button circleOnedrive;
    @FXML
    Button plusButton;
    @FXML
    Label infoText;
    
    CloudNet cloud = new CloudNet();
    UserDropBox drop = new UserDropBox();
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    public void handlePlusButtonClick(MouseEvent event) {
        circleDropbox.setVisible(true);
        circleGoogle.setVisible(true);
        circleOnedrive.setVisible(true);
    }
    
    public void handleDropboxCircleClick(MouseEvent event) {
        drop.initialDropboxSetup();
        changeToActivateScreen();
    }
    
    public void handleGoogleCircleClick(MouseEvent event) {
        changeToActivateScreen();
    }
    
    public void handleOneDriveCircleClick(MouseEvent event) {
        changeToActivateScreen();
    }
    
    private void changeToActivateScreen() {
        plusButton.setVisible(false);
        circleDropbox.setVisible(false);
        circleGoogle.setVisible(false);
        circleOnedrive.setVisible(false);   
        infoText.setVisible(false);
//        FXMLLoader lo = new FXMLLoader(CloudNet.class.getResource("noAccountsScreen.fxml"));
//        lo.setController(homeControl);
    }
    
    public void logout(MouseEvent event)throws IOException {
        cloud.logUserOut();
    }
    
}
