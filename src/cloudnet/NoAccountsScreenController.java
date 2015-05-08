/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cloudnet;

import cloudnet.user.UserDropBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    }
    
    public void handleGoogleCircleClick(MouseEvent event) {
        
    }
    
    public void handleOneDriveCircleClick(MouseEvent event) {
        
    }
    
    public void logout(MouseEvent event)throws IOException {
        cloud.logUserOut();
    }
    
}
