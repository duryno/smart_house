/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudnet.user;

import cloudnet.CloudNet;
import cloudnet.NoAccountsScreenController;
import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import com.dropbox.core.DbxWriteMode;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author davidmunro
 */
public class UserDropBox {

    static DbxClient cliee;
    private static final String DROPBOX_URL = "http://jorsino.com/cloudnet/addDropboxToken.php";    
    public static final String DROPBOX_CLOUD = "dropboxCloud";    

    final String keyForApp = "3arl279eij5125u";
    final String secretKeyForApp = "ic83wodtpty04ut";
    static String accessToken;
    private DbxClient dd;
    public DbxAppInfo apps;
    public DbxRequestConfig conf;
    public DbxWebAuthNoRedirect auth;
    private static DbxEntry entry;

    private String dropBoxUserAccessToken;
    
    public static String pathName = "/";

    private String currentDirectory = "";

    static ArrayList<String> list = new ArrayList<>();

    public UserDropBox() {
    }

    public void initialDropboxSetup(String userType) {
        apps = new DbxAppInfo(keyForApp, secretKeyForApp);
        conf = new DbxRequestConfig("CloxudNet/1.0", Locale.getDefault().toString());
        auth = new DbxWebAuthNoRedirect(conf, apps);
        String authoriseAccess = auth.start();

        if(userType.equals(CloudNet.NEW_USER)) {
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
    }

    public void showCloud(String tokenn, String userType) {
        dropBoxUserAccessToken = tokenn;

        if (tokenn.trim().length() > 0) {
            try {
                accessToken = tokenn;
                
                if(userType.equals(CloudNet.NEW_USER)) {
                    DbxAuthFinish finish = auth.finish(accessToken);
                    accessToken = finish.accessToken;
                }
                cliee = new DbxClient(conf, accessToken);

                final DbxEntry.WithChildren file = cliee.getMetadataWithChildren("/");
                ArrayList<String> listOfFiles2 = new ArrayList<>();

                for (DbxEntry c : file.children) {
                    listOfFiles2.add(c.name);
                }

                final ObservableList<String> itemss = FXCollections.observableArrayList(listOfFiles2);
                //CloudNet.noAccounts.clearTextView();
                if(userType.equals(CloudNet.EXISTING_USER))
                    CloudNet.noAccounts.setListItems(itemss, DROPBOX_CLOUD);

                displayFolder("", cliee, "forward");

                //addMenu();
                if(userType.equals(CloudNet.NEW_USER)) {
                    addTokenToDatabase(accessToken);
                    User.setDropboxToken(String.valueOf(accessToken));
                }

            } catch (DbxException ex) {
                //add error message here as well
                System.out.println("The token added was wrong : " + ex);
                ex.printStackTrace();
            }
        } else {
            //need to add error message here and send user back to adding token
            System.out.println("nothing entered in token area");
        }
    }

    public static void displayFolder(String folderName, DbxClient client, String direction) {
         if (direction.equals("forward")) {
            pathName = pathName.concat(folderName);
        } else if (direction.equals("backward")) {
            pathName = folderName;
        }
        try {
            //pathName = pathName.concat(folderName);
            DbxEntry.WithChildren file = cliee.getMetadataWithChildren(pathName);
            pathName = pathName.concat("/");

            ArrayList<String> list = new ArrayList<>();

            for (DbxEntry c : file.children) {
                list.add(c.name);
            }

            final ObservableList<String> itemss = FXCollections.observableArrayList(list);

            CloudNet.noAccounts.setListItems(itemss, DROPBOX_CLOUD);
        } catch (DbxException ex) {
            System.out.println("Client not recognised ! Error: " + ex);
        } catch (Exception e) {
            //if it is a file just reset the pathname at the moment.
            pathName = "/";
        }
    }

    public void addMenu() {
        NoAccountsScreenController.copy.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                uploadFile();
            }
        });
        NoAccountsScreenController.paste.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                System.out.println("paste");
            }
        });
        NoAccountsScreenController.download.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                downLoadFile();
            }
        });
        NoAccountsScreenController.fileFolderProperties.setOnAction(new EventHandler<ActionEvent>() {

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
                    NoAccountsScreenController.choiceMenu.hide();

                    JFileChooser savefile = new JFileChooser();
                    savefile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    int cho = savefile.showSaveDialog(null);

                    if (cho == JFileChooser.APPROVE_OPTION) {
                        String selectedDirectory = savefile.getSelectedFile().getAbsolutePath();
                        System.out.println("The selected directory was : " + selectedDirectory);

                        File file = new File(selectedDirectory + "\\" + currentDirectory);
                        file.createNewFile();                
                        fos = new FileOutputStream(file);

                        DbxEntry ee = cliee.getMetadata("/" + currentDirectory);
                        if (ee.isFolder()) {
                            //need to add some error code
                            System.out.println("TRIED TO DOWNLOAD FOLDER");
                        } else {
                            DbxEntry down = cliee.getFile("/" + currentDirectory, null, fos);
                            System.out.println("File downloaded to : " + selectedDirectory);

                        }
                        fos.close();
                    }

                } catch (FileNotFoundException ex) {
                    System.out.println("Access denied " + ex);
                } catch (IOException | DbxException | ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    System.out.println("Error" + ex);
                }
            }
        };
        Thread thr = new Thread(run);
        thr.setDaemon(true);
        thr.start();
    }

    public void uploadFile() {
        
        Runnable run = new Runnable() {

            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                    FileInputStream fis = null;

                    System.out.println("the curr : " + currentDirectory);

                    JFileChooser saveFile = new JFileChooser();     
                    File inputFile; // = new File("");
                    saveFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    int cho = saveFile.showOpenDialog(null);

                    if (cho == JFileChooser.APPROVE_OPTION) {
                        String selectedDirectory = saveFile.getSelectedFile().getAbsolutePath();
                        String selectedFile = saveFile.getSelectedFile().getName();
                        System.out.println("The current file that wants to be downloaded is : " + selectedDirectory);
                        inputFile = new File(selectedDirectory);
                        System.out.println("input file = " + inputFile.toString());
                        fis = new FileInputStream(inputFile);

                        DbxEntry.File fileToUpload = cliee.uploadFile("/" + selectedFile, DbxWriteMode.add(), inputFile.length(), fis);
                        System.out.println(fileToUpload.asFile().toStringMultiline());
                    }

                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | DbxException | IOException ex) {
                    Logger.getLogger(UserDropBox.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        Thread thread = new Thread(run);
        thread.setDaemon(true);
        thread.start();

    }
    
    public void uploadDroppedFile(final File file) {
        Runnable run = new Runnable() {

            @Override
            public void run() {
                try {
                    FileInputStream fis = null;                        
                    File fileToUpload = new File(file.getAbsolutePath());
                    fis = new FileInputStream(fileToUpload);

                    cliee.uploadFile("/" + file.getName(), DbxWriteMode.add(), fileToUpload.length(), fis);                   

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(run);
        thread.setDaemon(true);
        thread.start();
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
        String parameters = "user_id="+User.getId()+"&dropbox="+theTokenToAdd+"&box=null&google_drive=null";
        CloudNet.connector.sendToDatabase(parameters, DROPBOX_URL);
    }
    
    public void setCurrentDirectory(String dir) {
        currentDirectory = dir;
    }
    
    public String getCurrentDirectory() {
        return currentDirectory;
    }
    
    public DbxClient getCliee() {
        return cliee;
    }
    
}
