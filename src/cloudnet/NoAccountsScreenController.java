/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cloudnet;

import static cloudnet.CloudNet.homeControl;
import static cloudnet.CloudNet.stagey;
import cloudnet.user.User;
import cloudnet.user.UserDropBox;
import cloudnet.user.UserGoogleDrive;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.google.api.services.drive.model.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

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
    static String accessToken;

    @FXML
    Button button;
    @FXML
    static Button activateButton;
    @FXML
    Button addCloud;
    @FXML
    AnchorPane content;
    @FXML
    AnchorPane anc;
    @FXML
    public static TextField tokenTextField;
    @FXML
    //public static ListView listFolders;
    public static AnchorPane listFolders;
    @FXML
    static ScrollPane scrollPane;
    @FXML
    public static ListView secondList;
    @FXML
    public static TextField directoryText;
    @FXML
    public static ContextMenu choiceMenu;
    @FXML
    public static MenuItem copy, paste, download, fileFolderProperties;
    @FXML
    Label[] labels;
    @FXML
    ImageView box;
    String cloudClicked;
    
    CloudNet cloud = new CloudNet();
    static UserDropBox drop = new UserDropBox();
    static UserGoogleDrive google = new UserGoogleDrive();

    private DbxClient dd;    
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        drop.initialDropboxSetup(CloudNet.EXISTING_USER);
        if(User.getDropboxToken() != null)
            drop.showCloud(User.getDropboxToken(), CloudNet.EXISTING_USER);
    }
    
    public void handlePlusButtonClick(MouseEvent event) {
        circleDropbox.setVisible(true);
        circleGoogle.setVisible(true);
        circleOnedrive.setVisible(true);
    }
    
    public void handleDropboxCircleClick(MouseEvent event) {
        drop.initialDropboxSetup(CloudNet.NEW_USER);
        changeToActivateScreen();
        cloudClicked = "dropbox";
    }
    
    public void handleGoogleCircleClick(MouseEvent event) {
        google.initialGoogleDriveSetup();
        changeToActivateScreen();
        cloudClicked = "google";
    }
    
    public void handleOneDriveCircleClick(MouseEvent event) {
        changeToActivateScreen();
    }
    
    private void changeToActivateScreen() {
        plusButton.setVisible(false);
        circleDropbox.setVisible(false);
        circleGoogle.setVisible(false);
        circleOnedrive.setVisible(false);
        listFolders.setVisible(false);
        scrollPane.setVisible(false);
        infoText.setVisible(false);
        tokenTextField.setVisible(true);
        activateButton.setVisible(true);
    }
    
    public void logout(MouseEvent event)throws IOException {
        cloud.logUserOut();
    }
    
    @FXML
    public void addCloud() throws IOException, DbxException {

        Runnable run = new Runnable() {

            @Override
            public void run() {
                activateButton.setVisible(true);
                tokenTextField.setVisible(true);

                drop.initialDropboxSetup(CloudNet.NEW_USER);

            }
        };
        Thread thr = new Thread(run);
        thr.setDaemon(true); //so when the main program ends, so will this thread.
        thr.start();

    }

    @FXML
    public void showCloud() throws DbxException, IOException {
        if(cloudClicked.equals("dropbox")) {
            drop.showCloud(tokenTextField.getText(), CloudNet.NEW_USER);
        } else if(cloudClicked.equals("google")) {
            google.printFilesInFolder(CloudNet.NEW_USER, "root", tokenTextField.getText());
        }
    }

    //moved to noAccounts
    public void logout() throws IOException {
        cloud.logUserOut();
    }

    @FXML
    public void setListItems(List ol, String cloud) {
        labels = new Label[ol.size()];
        listFolders.getChildren().removeAll(listFolders.getChildren());
        activateButton.setVisible(false);
        tokenTextField.setVisible(false);
        scrollPane.setVisible(true);
        listFolders.setMinHeight((ol.size()+3)/3*200);
        Pane folder;        
        for(int i=0; i<ol.size(); i++) { 
            folder = new Pane();
            folder.setMinSize(250, 200);
            folder.setLayoutX(i%3*250);
            folder.setLayoutY(i/3*200);
            if(cloud.equals(UserGoogleDrive.GOOGLE_CLOUD)) {
                ArrayList<File> files = (ArrayList<File>)ol;
                labels[i] = new Label(files.get(i).getTitle());
            } else
                labels[i] = new Label(ol.get(i).toString());
            labels[i].setLayoutY(170);
            labels[i].setMinWidth(250);
            labels[i].setMinHeight(30);
            labels[i].setAlignment(Pos.CENTER);
            labels[i].setId("i");
            folder.getChildren().add(labels[i]);
            folder.setStyle("-fx-border-width: 1px; -fx-border-color: #f0f0f0;");
            folder.setVisible(true);
            labels[i].setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    Object source = t.getSource();
                    Label clickedLabel = (Label)source;
                    if (t.getClickCount() == 2) {                       
                        drop.setCurrentDirectory(clickedLabel.getText());
                        drop.displayFolder(clickedLabel.getText(), dd);
                    } else {
                        drop.setCurrentDirectory(clickedLabel.getText());
                    }
                }
            });
            listFolders.getChildren().add(folder);
            listFolders.setVisible(true);                  
        }
        //listFolders.setItems(ol);
    }
    
    @FXML
    public void addMenu() {
        drop.addMenu();
    }
    
    @FXML
    public void setSecondList(ObservableList listt){
        //listFolders.setItems(listt);
        //secondList.setItems(listt);
    
    }
    
    @FXML
    public void clearTextView(){
        //tokenTextField.clear();
    }
    
}
