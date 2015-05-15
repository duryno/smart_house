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
    static Drive.Children.List request;
    static final HttpTransport httpTransport = new NetHttpTransport();
    static final JsonFactory jsonFactory = new JacksonFactory();
    static final GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE))
                .setAccessType("online")
                .setApprovalPrompt("auto").build();

    public void initialGoogleDriveSetup() {             

            String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
            System.out.println("Please open the following URL in your browser then type the authorization code:");
            Desktop desk = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            URI uri = URI.create(url);
            if (desk != null && desk.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desk.browse(uri);
                } catch (Exception e) {
                    System.out.println("Error in retrieving URI" + e);
                }
            }
        
            //System.out.println("  " + url);
            
            
    }         
    
    public void printFilesInFolder(String userType, String folderId, String token)
            throws IOException {
        
        final String type = userType;
        final String code = token;
        final String id = folderId;
        
//        Runnable run = new Runnable() {
//
//                @Override
//                public void run() {                    
                    
                    try {
                        GoogleTokenResponse response;
                        if (type.equals(CloudNet.NEW_USER)) {
                            //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                            //String code = ();

                            response = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();

                            addTokenToDatabase(code);
                        } else {
                            response = flow.newTokenRequest(User.getGoogleToken()).setRedirectUri(REDIRECT_URI).execute();
                        }
                        GoogleCredential credential = new GoogleCredential().setFromTokenResponse(response);
                        //Create a new authorized API client
                        service = new Drive.Builder(httpTransport, jsonFactory, credential).build();
                        request = service.children().list(id).setQ("trashed = false");
                        //printFilesInFolder("root");

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                
            
            
//            Thread thread = new Thread(run);
//            thread.setDaemon(true);
//            thread.start();
            
//        Drive.Children.List request = service.children().list(folderId).setQ("trashed = false");
        ArrayList<File> files = new ArrayList();
        do {
            try {
              ChildList children = request.execute();

              for (ChildReference child : children.getItems()) {
                File file = service.files().get(child.getId()).execute();
                System.out.println("File name: " + file.getTitle());
                files.add(file);
              }
              request.setPageToken(children.getNextPageToken());
            } catch (IOException e) {
              System.out.println("An error occurred: " + e);
              request.setPageToken(null);
            }
          } while (request.getPageToken() != null &&
                   request.getPageToken().length() > 0);
        
        CloudNet.noAccounts.setListItems(files, GOOGLE_CLOUD);
    }

    
    private void addTokenToDatabase(String theTokenToAdd) {
        String parameters = "user_id="+User.getId()+"&dropbox=null&onedrive=null&google_drive="+theTokenToAdd;
        CloudNet.connector.sendToDatabase(parameters, GOOGLE_URL);
    }
    
}
