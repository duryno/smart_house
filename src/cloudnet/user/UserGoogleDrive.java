/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudnet.user;

import cloudnet.CloudNet;
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
import java.awt.Desktop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

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
        GoogleCredential credential;
        ArrayList<File> files = new ArrayList();

        try {
            GoogleTokenResponse response;
            if (userType.equals(CloudNet.NEW_USER)) {
                response = flow.newTokenRequest(token).setRedirectUri(REDIRECT_URI).execute();

                System.out.println("access: " + response.getAccessToken());
                System.out.println("referesh: " + response.getRefreshToken());
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

                filesRequest.setPageToken(filesList.getNextPageToken());
            } catch (IOException e) {
                System.out.println("An error occurred: " + e);
                filesRequest.setPageToken(null);
            }
        } while (filesRequest.getPageToken() != null
                && filesRequest.getPageToken().length() > 0);

        CloudNet.noAccounts.setListItems(files, GOOGLE_CLOUD);
    }

    
    private void addTokenToDatabase(String theTokenToAdd) {
        String parameters = "user_id="+User.getId()+"&dropbox=null&box=null&google_drive="+theTokenToAdd;
        CloudNet.connector.sendToDatabase(parameters, GOOGLE_URL);
    }
    
}
