/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cloudnet;

import static cloudnet.CloudNet.stagey;
import cloudnet.user.User;
import cloudnet.user.UserDropBox;
import cloudnet.user.UserGoogleDrive;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.google.api.services.drive.model.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
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
    Pane[] folders;
    @FXML
    Button dropboxIcon;
    @FXML
    Button googleIcon;
    @FXML
    Button oneDriveIcon;
    String cloudClicked = "";
    
    CloudNet cloud = new CloudNet();
    static UserDropBox drop = new UserDropBox();
    static UserGoogleDrive google = new UserGoogleDrive();

    List<File> googleFiles = new ArrayList<>();
    int filesIndex = 0;
    private DbxClient dd;    
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {     
        if(!User.getDropboxToken().equals("null")) {
            drop.initialDropboxSetup(CloudNet.EXISTING_USER);
            drop.showCloud(User.getDropboxToken(), CloudNet.EXISTING_USER);
        } else if(!User.getGoogleToken().equals("null"))    
            google.printFilesInFolder(CloudNet.EXISTING_USER, "root", User.getGoogleToken());
                    
        if(User.getDropboxToken() != null && !User.getDropboxToken().equals("null"))
            showConnectedIcon(dropboxIcon);
        if(User.getGoogleToken() != null && !User.getGoogleToken().equals("null"))
            showConnectedIcon(googleIcon);
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
        scrollPane.setVisible(false);
        listFolders.setVisible(false);
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
        tokenTextField.clear();
    }

    //moved to noAccounts
    public void logout() throws IOException {
        cloud.logUserOut();
    }

    @FXML
    public void setListItems(List ol, String cloud) {
        googleFiles = ol;
        labels = new Label[ol.size()];
        folders = new Pane[ol.size()];
        listFolders.getChildren().removeAll(listFolders.getChildren());
        activateButton.setVisible(false);
        tokenTextField.setVisible(false);
        scrollPane.setVisible(true);
        listFolders.setMinHeight((ol.size()+2)/3*200);
        String type = null;        
    
        for(int i=0; i<ol.size(); i++) { 
            folders[i] = new Pane();   
            folders[i].setId(String.valueOf(i));
            folders[i].setMinSize(250, 200);
            folders[i].setLayoutX(i%3*250);
            folders[i].setLayoutY(i/3*200);            
            
            if(cloud.equals(UserGoogleDrive.GOOGLE_CLOUD)) {
                ArrayList<File> files = (ArrayList<File>)ol;
                labels[i] = new Label(files.get(i).getTitle());
            } else
                labels[i] = new Label(ol.get(i).toString());
            
            if(labels[i].getText().length() > 3 && 
                    labels[i].getText().charAt(labels[i].getText().length()-4) == '.')
                type = "file";
            else if(labels[i].getText().length() > 4 && 
                    labels[i].getText().charAt(labels[i].getText().length()-5) == '.')
                type = "file";                
            else 
                type = "folder";
            
            if(labels[i].getText().length() > 25)
                labels[i].setText(labels[i].getText().substring(0, 25)+"...");
            labels[i].setLayoutY(170);
            labels[i].setMinWidth(250);
            labels[i].setMinHeight(30);
            labels[i].setAlignment(Pos.CENTER);            
                        
            folders[i].getChildren().add(labels[i]);
            folders[i].getStyleClass().add(type);
            folders[i].setVisible(true);
            
            folders[i].setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    Object source = t.getSource();
                    Pane clickedFolder = (Pane)source;
                    if(cloudClicked.equals("dropbox")) {
                        Label l = (Label)clickedFolder.getChildren().get(0);
                        if (t.getClickCount() == 2) {                                               
                            drop.setCurrentDirectory(l.getText());
                            drop.displayFolder(l.getText(), dd);
                        } else {
                            drop.setCurrentDirectory(l.getText());
                        }
                    } else {
                        if (t.getClickCount() == 2) 
                            google.printFilesInFolder(CloudNet.EXISTING_USER, 
                                    googleFiles.get(Integer.valueOf(clickedFolder.getId())).getId(), User.getGoogleToken());
                    }
                }
            });
            listFolders.getChildren().add(folders[i]);
            listFolders.setVisible(true);    
        }
    }
        
    @FXML
    public void handleTopDropboxIconClick() {
        if(User.getDropboxToken().equals("null")) {
            drop.initialDropboxSetup(CloudNet.NEW_USER);
            changeToActivateScreen();
        } else {
            drop.initialDropboxSetup(CloudNet.EXISTING_USER);
            drop.showCloud(User.getDropboxToken(), CloudNet.EXISTING_USER);
            selectIcon(dropboxIcon);
            System.out.println("handle clicked");
        }
        cloudClicked = "dropbox";
    }
    
    @FXML
    public void handleTopGoogleIconClick() {        
        cloudClicked = "google";
        if(User.getGoogleToken().equals("null")) {
            google.initialGoogleDriveSetup();
            changeToActivateScreen();
        } else {     
            google.printFilesInFolder(CloudNet.EXISTING_USER, "root", User.getGoogleToken());                
            selectIcon(googleIcon);                
        }
        
    }
    
    private void selectIcon(Node n) {
        n.setOpacity(1);
        unselectIcon();
    }
    
    private void unselectIcon() {
        Node n = null;
        switch(cloudClicked) {
            case "google": 
                n = googleIcon;
                break;
            case "dropbox":
                n = dropboxIcon;
                break;
        }
        n.setOpacity(0.5);
    }
    
    private void showConnectedIcon(Node n) {
        n.setOpacity(0.5);
    }
    
    @FXML
    public void dropboxIconDragAndDrop(DragEvent event) {
        System.out.println("drag");
        drop.uploadFile();
    }
    
    @FXML
    public void scrollPaneDragAndDrop(DragEvent event) {
        System.out.println("scroll drag");
        if(cloudClicked.equals("dropbox"))
            drop.uploadFile();
    }
    
    @FXML
    public void addMenu() {
        drop.addMenu();
    }
    
}
