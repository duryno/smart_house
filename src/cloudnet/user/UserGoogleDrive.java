/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudnet.user;

import cloudnet.CloudNet;
import cloudnet.NoAccountsScreenController;
import static cloudnet.user.UserDropBox.cliee;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.ChildList;
import com.google.api.services.drive.model.ChildReference;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.ParentList;
import java.awt.Desktop;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Juraj
 */
public class UserGoogleDrive {

    private static String CLIENT_ID = "181058552670-oncn6o3a1ld6rs365vpbbhanu2fpjrqn.apps.googleusercontent.com";
    private static String CLIENT_SECRET = "_67kC1c59Cryy_JHgSK4E6U2";
    private static final String GOOGLE_URL = "http://jorsino.com/cloudnet/addGoogleToken.php";
    public static final String GOOGLE_CLOUD = "googleCloud";    
    private static String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
    
    static ArrayList<File> files = new ArrayList(); 
    static ArrayList<String> levels = new ArrayList<String>();
    static int level = 0;
    static String fileToDownloadUrl;
    static String fileToDownloadId;
    static String fileToDownloadTitle;
    
    ParentList parents;
    static Drive service;    
    static Drive.Files.List filesRequest;
    static final HttpTransport httpTransport = new NetHttpTransport();
    static final JsonFactory jsonFactory = new JacksonFactory();
    static final GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE))
                .setAccessType("offline")
                .setApprovalPrompt("auto").build();

    public void initialGoogleDriveSetup() {            
        String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
        Desktop desk = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        URI uri = URI.create(url);
        if (desk != null && desk.isSupported(Desktop.Action.BROWSE)) {
            try {
                desk.browse(uri);
            } catch (Exception e) {
                System.out.println("Error in retrieving URI" + e);
            }
        }                        
    }         
    
    public void printFilesInFolder(String userType, String folderId, String token) { 
//        if(!files.isEmpty())
//            parentFolderId = files.get(0).getParents().get(0).getId();
        GoogleCredential credential;
        files.removeAll(files);

        try {
            GoogleTokenResponse response;
            if (userType.equals(CloudNet.NEW_USER)) {
                response = flow.newTokenRequest(token).setRedirectUri(REDIRECT_URI).execute();

                addTokenToDatabase(response.getRefreshToken());
                User.setGoogleToken(String.valueOf(response.getRefreshToken()));

                credential = new GoogleCredential().setAccessToken(response.getAccessToken());
            } else {
                GoogleCredential.Builder b = new GoogleCredential.Builder();
                b.setJsonFactory(jsonFactory);
                b.setTransport(httpTransport);
                b.setClientSecrets(CLIENT_ID, CLIENT_SECRET);
                credential = b.build();
                credential.setRefreshToken(token);
                credential.refreshToken();
                credential.setAccessToken(credential.getAccessToken());
            }

            //Create a new authorized API client
            service = new Drive.Builder(httpTransport, jsonFactory, credential).setApplicationName("CloudNet").build();
            filesRequest = service.files().list().setQ("trashed = false and '" + folderId + "' in parents");
            

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        do {
            try {
                FileList filesList = filesRequest.execute();

                for (File file : filesList.getItems()) {
                    files.add(file);
                    System.out.println(file.getTitle());
                }
                //parentFolderId = files.get(0).getParents().get(0).getId();

                filesRequest.setPageToken(filesList.getNextPageToken());
            } catch (IOException e) {
                System.out.println("An error occurred: " + e);
                filesRequest.setPageToken(null);
            }
        } while (filesRequest.getPageToken() != null
                && filesRequest.getPageToken().length() > 0);

        addMenu();
        CloudNet.noAccounts.setListItems(files, GOOGLE_CLOUD);
    }
    
    public void printFilesInParent() {
        level--;        
        String id = levels.get(level);
        printFilesInFolder(CloudNet.EXISTING_USER, id, User.getGoogleToken());
        levels.remove(levels.size()-1);
    }
    
    public void downloadFile() {
        Runnable run = new Runnable() {

            @Override
            public void run() {
                try {
                    FileOutputStream fos = null;
                    InputStream is = null;
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    NoAccountsScreenController.choiceMenu.hide();

                    JFileChooser savefile = new JFileChooser();
                    savefile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    int cho = savefile.showSaveDialog(null);

                    if (cho == JFileChooser.APPROVE_OPTION) {
                        String selectedDirectory = savefile.getSelectedFile().getAbsolutePath();
                        System.out.println("The selected directory was : " + selectedDirectory);
                                                
                        if (fileToDownloadUrl != null && fileToDownloadUrl.length() > 0) {
                            try {
                                // uses alt=media query parameter to request content
                                is = service.files().get(fileToDownloadId).executeMediaAsInputStream();
                            } catch (IOException e) {
                                // An error occurred.
                                e.printStackTrace();
                                //return null;
                            }
                        } else {
                            // The file doesn't have any content stored on Drive.
                            //return null;
                        }

                        java.io.File file = new java.io.File(selectedDirectory + "//" + fileToDownloadTitle);
                        file.createNewFile();                
                        fos = new FileOutputStream(file);
                        
                        int read = 0;
                        byte[] bytes = new byte[1024];

                        while ((read = is.read(bytes)) != -1) {
                                fos.write(bytes, 0, read);
                        }

                        fos.close();
                    }

                } catch (Exception ex) {
                    System.out.println("Access denied " + ex);
                } 
            }
        };
        Thread thr = new Thread(run);
        thr.start();
    }
    
    public void uploadFile() {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ex) {

                }
                String type = null;
                NoAccountsScreenController.choiceMenu.hide();
                JFileChooser saveFile = new JFileChooser();
                saveFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int cho = saveFile.showOpenDialog(null);
                if (cho == JFileChooser.APPROVE_OPTION) {
                    String selectedDirectory = saveFile.getSelectedFile().getAbsolutePath();
                    
                    File body = new File();
                    body.setTitle(saveFile.getSelectedFile().getName());

                    Path p = Paths.get(selectedDirectory, "");

                    try {
                        type = Files.probeContentType(p);
                    } catch (IOException ex) {

                    }

                    java.io.File fileContent = new java.io.File(selectedDirectory);
                    FileContent mediaContent = new FileContent(type, fileContent);

                    try {
                        File file = service.files().insert(body, mediaContent).execute();
                    } catch (IOException ex) {

                    }
                }
            }
        };       
        Thread t = new Thread(runnable);
        t.start();
        
    }
    
    public void uploadDroppedFile(final java.io.File file) {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                String type = null;
                    
                    File body = new File();
                    body.setTitle(file.getName());

                    Path p = Paths.get(file.getAbsolutePath(), "");

                    try {
                        type = Files.probeContentType(p);
                    } catch (IOException ex) {

                    }

                    java.io.File fileContent = new java.io.File(file.getAbsolutePath());
                    FileContent mediaContent = new FileContent(type, fileContent);

                    try {
                        File file = service.files().insert(body, mediaContent).execute();
                    } catch (IOException ex) {

                    }
                }
            
        };       
        Thread t = new Thread(runnable);
        t.start();
    }
    
    private void addTokenToDatabase(String theTokenToAdd) {
        String parameters = "user_id="+User.getId()+"&dropbox=null&box=null&google_drive="+theTokenToAdd;
        CloudNet.connector.sendToDatabase(parameters, GOOGLE_URL);
    }
    
    public void increaseLevel() {
        level++;
    }
    
    public void addLevel(String folderId) {
        if(!levelsContainsId(folderId))
            levels.add(folderId);
    }
    
    private boolean levelsContainsId(String id) {
        for(String s: levels) {
            if(s.equals(id))
                return true;              
        }
        return false;
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
                downloadFile();
            }
        });
        NoAccountsScreenController.fileFolderProperties.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                //showProperties();
            }
        });
    }
    
    public void setFileToDownloadUrl(String url) {
        fileToDownloadUrl = url;
    }
    
    public void setFileToDownloadId(String id) {
        fileToDownloadId = id;
    }
    
    public void setFileToDownloadTitle(String title) {
        fileToDownloadTitle = title;
    }
        
}
