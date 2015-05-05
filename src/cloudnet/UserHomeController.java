/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudnet;

import cloudnet.user.UserDropBox;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javax.imageio.ImageIO;

/**
 *
 * @author davidmunro
 */
public class UserHomeController implements Initializable {

    static String accessToken;

    @FXML
    Button button, activateButton, addCloud;
    @FXML
    AnchorPane content;
    @FXML
    AnchorPane anc;
    @FXML
    public static TextField tokenTextField;
    @FXML
    public static ListView listFolders;
    @FXML
    public static ListView secondList;

    CloudNet cloud = new CloudNet();
    UserDropBox drop = new UserDropBox(accessToken);

    final String keyForApp = "3arl279eij5125u";
    final String secretKeyForApp = "ic83wodtpty04ut";
    private DbxClient dd;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void addCloud() throws IOException, DbxException {

        Runnable run = new Runnable() {

            @Override
            public void run() {
                activateButton.setVisible(true);
                tokenTextField.setVisible(true);

                drop.intialDropboxSetup(keyForApp, secretKeyForApp);

            }
        };
        Thread thr = new Thread(run);
        thr.setDaemon(true); //so when the main program ends, so will this thread.
        thr.start();

    }

    @FXML
    public void showCloud() throws DbxException, IOException {
        drop.showCloud(tokenTextField.getText());
    }

    public void logout() throws IOException {
        cloud.logUserOut();
    }

    @FXML
    public void setListItems(ObservableList ol) {
        listFolders.setItems(ol);
    }
    
    @FXML
    public void setSecondList(ObservableList listt){
        listFolders.setItems(listt);
        //secondList.setItems(listt);
        
    }
    
    @FXML
    public void clearTextView(){
        tokenTextField.clear();
    }
}
