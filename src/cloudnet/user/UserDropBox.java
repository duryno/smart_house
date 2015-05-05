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
import java.awt.Desktop;
import java.net.URI;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author davidmunro
 */
public class UserDropBox {

    DbxClient cliee;

    final String keyForApp = "3arl279eij5125u";
    final String secretKeyForApp = "ic83wodtpty04ut";
    static String accessToken;
    private DbxClient dd;
    public DbxAppInfo apps;
    public DbxRequestConfig conf;
    public DbxWebAuthNoRedirect auth;

    private String dropBoxUserAccessToken;
    
    private String pathName = "/";

    public UserDropBox(String dropBoxAccessToken) {
        this.dropBoxUserAccessToken = dropBoxAccessToken;
    }

    public void intialDropboxSetup(String key, String secret) {
        apps = new DbxAppInfo(key, secret);
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

                displayFolder(cliee);

                addTokenToDatabase(dropBoxUserAccessToken);

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
                        displayFolder(UserHomeController.listFolders.getSelectionModel().getSelectedItem().toString(), cliee);
                    }
                }
            });

        } catch (DbxException ex) {
            System.out.println("Client not recognised ! Error: " + ex);
        } catch(Exception e){
            //if it is a file jsut reset the pathname at the moment.
            pathName = "/";
        }
    }

    public void displayFolder(DbxClient client) {
        try {
            System.out.println(pathName);
            DbxEntry.WithChildren file = client.getMetadataWithChildren(pathName);

            System.out.println(file.children.toString());

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
                        System.out.println(UserHomeController.listFolders.getSelectionModel().getSelectedItem().toString());
                        displayFolder(UserHomeController.listFolders.getSelectionModel().getSelectedItem().toString(), cliee);
                    }

                }
            });
        } catch (DbxException ex) {
            Logger.getLogger(UserDropBox.class.getName()).log(Level.SEVERE, null, ex);
        } catch(Exception e){
            System.out.println(e);
            pathName = "/";
        }
    }

    private void addTokenToDatabase(String theTokenToAdd) {

    }
}
