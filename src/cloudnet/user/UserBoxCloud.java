
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudnet.user;

import cloudnet.CloudNet;
import cloudnet.NoAccountsScreenController;
import com.box.sdk.BoxAPIConnection;
import com.box.sdk.BoxFile;
import com.box.sdk.BoxFolder;
import com.box.sdk.BoxItem;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author davidmunro
 */
public class UserBoxCloud {

    private static final String BOX_URL = "http://jorsino.com/cloudnet/addBoxToken.php";
    private static final String CLIENT_ID = "8v41an50hm4u2oa3lpotihiqsg8mjsyk";
    private static final String CLIENT_SECRET = "e8TOjjFfpVO93cecgdyKm5RDrHSs4vC3";
    private static final String BOX_CLOUD = "boxCloud";
    private static final String AUTH_CODE = null;

    private static BoxAPIConnection conn;
    private String currentDir;

    public UserBoxCloud() {
    }

    public void initialBoxSetUp(String userType) {       
        if (userType.equals(CloudNet.NEW_USER)) {
            Desktop desk = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;

            String parameters = "response_type=code&client_id=" + CLIENT_ID + "&state=security_token";
            URI uri = URI.create("https://app.box.com/api/oauth2/authorize" + "?" + parameters);
            if (desk != null && desk.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desk.browse(uri);
                } catch (Exception e) {
                    System.out.println("Error in retrieving URI" + e);
                }
            }
        }
    }

    public void showBox(String code) {
        conn = new BoxAPIConnection(CLIENT_ID, CLIENT_SECRET, code);  
        User.setBoxToken(code);
        displayFolder();
    }

    public void displayFolder() {
              
        BoxFolder folder = BoxFolder.getRootFolder(conn);

        ArrayList<String> list = new ArrayList<>();
        for (BoxItem.Info item : folder) {
            list.add(item.getName());
        }

        final ObservableList obList = FXCollections.observableArrayList(list);
        CloudNet.noAccounts.setListItems(obList, BOX_CLOUD);

    }

    private void downloadFile() {
        Runnable run = new Runnable() {

            @Override
            public void run() {
                try {
                    FileOutputStream fos = null;
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    NoAccountsScreenController.choiceMenu.hide();

                    String fileToDownload = getCurrentDirectory();

                    JFileChooser savefile = new JFileChooser();
                    savefile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    int cho = savefile.showSaveDialog(null);

                    if (cho == JFileChooser.APPROVE_OPTION) {
                        String selectedDirectory = savefile.getSelectedFile().getAbsolutePath();
                        System.out.println("The selected directory was : " + selectedDirectory);
                        System.out.println("the file to download is : " + fileToDownload);

                        File file2 = new File(selectedDirectory + "\\" + getCurrentDirectory());
                        file2.createNewFile();
                        fos = new FileOutputStream(file2);
                        
                        BoxFile file = new BoxFile(conn, getCurrentDirectory());
                        BoxFile.Info fileInfo = file.getInfo();
                        
                        fos = new FileOutputStream(file2);
                        file.download(fos);
                        
                        fos.close();
                    }

                } catch (FileNotFoundException ex) {
                    System.out.println("Access denied " + ex);
                } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException ee) {
                    System.out.println("Error" + ee);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(UserBoxCloud.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        Thread thr = new Thread(run);
        thr.setDaemon(true);
        thr.start();
        
    }

    private void addBoxTokenToDatabase(String tokenToAdd) {
        String parameters = "user_id=" + User.getId() + "&dropbox=&box=" + tokenToAdd + "&google_drive=null";
        CloudNet.connector.sendToDatabase(parameters, BOX_URL);
    }
    
    public void setCurrentDirectory(String dir){
        currentDir = dir;
    }
    
    public String getCurrentDirectory(){
        return currentDir;
    }
}‏








