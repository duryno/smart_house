/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudnet.user;

import cloudnet.CloudNet;
import cloudnet.FXMLDocumentController;
import cloudnet.UserHomeController;
import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dialog;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author davidmunro
 */
public class UserDropBox {

    DbxClient cliee;
    private static final String DROPBOX_URL = "http://jorsino.com/cloudnet/addDropboxToken.php";

    final String keyForApp = "3arl279eij5125u";
    final String secretKeyForApp = "ic83wodtpty04ut";
    static String accessToken;
    private DbxClient dd;
    public DbxAppInfo apps;
    public DbxRequestConfig conf;
    public DbxWebAuthNoRedirect auth;
    private static DbxEntry entry;

    private String dropBoxUserAccessToken;
    
    private String pathName = "/";

    private String currentDirectory = "";

    static ArrayList<String> list = new ArrayList<>();

    public UserDropBox() {
    }

    public void initialDropboxSetup() {
        apps = new DbxAppInfo(keyForApp, secretKeyForApp);
        conf = new DbxRequestConfig("CloudNet/1.0", Locale.getDefault().toString());
        auth = new DbxWebAuthNoRedirect(conf, apps);
        String authoriseAccess = auth.start();

        Desktop desk = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        URI uri = URI.create(authoriseAccess);
        if (desk != null && desk.isSupported(Desktop.Action.BROWSE)) {
            try {
                desk.browse(uri);
            } catch (Exception e) {
                System.out.println("Error in retrieving URI" + e);
            }
        }
    }

    public void showCloud(String tokenn) {
        dropBoxUserAccessToken = tokenn;

        if (tokenn.trim().length() > 0) {
            try {
                accessToken = tokenn;
                DbxAuthFinish finish = auth.finish(accessToken);
                cliee = new DbxClient(conf, finish.accessToken);

                final DbxEntry.WithChildren file = cliee.getMetadataWithChildren("/");
                ArrayList<String> listOfFiles2 = new ArrayList<>();

                for (DbxEntry c : file.children) {
                    listOfFiles2.add(c.name);
                }

                final ObservableList<String> itemss = FXCollections.observableArrayList(listOfFiles2);
                CloudNet.homeControl.clearTextView();

                displayFolder("", cliee);

                addMenu();
                addTokenToDatabase(tokenn);

            } catch (DbxException ex) {
                //add error message here as well
                System.out.println("The token added was wrong : " + ex);
            }
        } else {
            //need to add error message here and send user back to adding token
            System.out.println("nothing entered in token area");
        }
    }

    private void displayFolder(String folderName, DbxClient client) {
        try {
            pathName = pathName.concat(folderName);
            DbxEntry.WithChildren file = client.getMetadataWithChildren(pathName);
            pathName = pathName.concat("/");

            ArrayList<String> list = new ArrayList<>();

            for (DbxEntry c : file.children) {
                list.add(c.name);
            }

            final ObservableList<String> itemss = FXCollections.observableArrayList(list);

            CloudNet.homeControl.setListItems(itemss);

            UserHomeController.listFolders.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    if (t.getClickCount() == 2) {
                        currentDirectory = UserHomeController.listFolders.getSelectionModel().getSelectedItem().toString();
                        displayFolder(UserHomeController.listFolders.getSelectionModel().getSelectedItem().toString(), cliee);
                    } else {
                        currentDirectory = UserHomeController.listFolders.getSelectionModel().getSelectedItem().toString();
                    }
                }
            });

        } catch (DbxException ex) {
            System.out.println("Client not recognised ! Error: " + ex);
        } catch (Exception e) {
            //if it is a file just reset the pathname at the moment.
            pathName = "/";
        }
    }

    public void addMenu() {
        UserHomeController.copy.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                System.out.println("copy");
            }
        });
        UserHomeController.paste.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                System.out.println("paste");
            }
        });
        UserHomeController.download.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                downLoadFile();
            }
        });
        UserHomeController.fileFolderProperties.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                showProperties();
            }
        });
    }

     public void downLoadFile() {

        Runnable run = new Runnable() {

            @Override
            public void run() {
                try {
                    FileOutputStream fos = null;

                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                    UserHomeController.choiceMenu.hide();

                    String filename = currentDirectory;
                    JFileChooser savefile = new JFileChooser();
                    savefile.setSelectedFile(new File(filename));
                    savefile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    savefile.setApproveButtonText("Download file");
                    savefile.setForeground(Color.BLUE);
                    int cho = savefile.showSaveDialog(null);

                    if (cho == JFileChooser.APPROVE_OPTION) {
                        String selectedDirectory = savefile.getCurrentDirectory().toString();
                        System.out.println("The current directory is : " + selectedDirectory);

                        File file = new File(selectedDirectory);
                        fos = new FileOutputStream(file);
                        DbxEntry.File download = cliee.getFile("/" + currentDirectory, null, fos);
                        System.out.println("File downloaded to : " + selectedDirectory);
                        fos.close();
                    } else {
                        savefile.setOpaque(true);
                    }

                } catch (FileNotFoundException ex) {
                    System.out.println("Access denied " + ex);
                } catch (IOException | DbxException | ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    System.out.println("Error" + ex);
                }
            }
        };
        Thread thr = new Thread(run);
        thr.setDaemon(true); //so when the main program ends, so will this thread.
        thr.start();

    }

    private void uploadFile() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(CloudNet.stagey);

        if (file != null) {

            String fileName = file.getName();
            String fileExtension = fileName.substring(fileName.indexOf(".") + 1, file.getName().length());
            System.out.println(">> fileExtension" + fileExtension);

        }

    }

    private void showProperties() {
        try {
            DbxEntry ee = cliee.getMetadata("/" + currentDirectory);
            System.out.println(ee.toStringMultiline());
        } catch (DbxException ex) {
            System.out.println("Error displaying properties : " + ex);
        }
    }

    private void addTokenToDatabase(String theTokenToAdd) {
        String parameters = "user_id=1&dropbox="+theTokenToAdd+"&onedrive=null&google_drive=null";
        sendToDatabase(parameters, DROPBOX_URL);
    }
    
    private void sendToDatabase(String parameters, String address) {
        String line = null;
        try {            
            URL url = new URL(address);
            HttpURLConnection hp=(HttpURLConnection)url.openConnection();
            hp.setDoInput(true);
            hp.setDoOutput(true);
            hp.setInstanceFollowRedirects(false);
            hp.setRequestMethod("POST");
            hp.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
            hp.setRequestProperty("charset", "utf-8");
            hp.setRequestProperty("Content-Length", "" + Integer.toString(parameters.getBytes().length));
            hp.setUseCaches (false);
            DataOutputStream wr = new DataOutputStream(hp.getOutputStream ());
            wr.writeBytes(parameters);
            wr.flush();            
            BufferedReader reader = new BufferedReader(new InputStreamReader(hp.getInputStream()));
            while(reader.ready()) {
                line = reader.readLine();            
            }
            System.out.println(line);
            wr.close();
            reader.close();
            hp.disconnect();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
                
        //return checkResult(line);
    }
    
}
