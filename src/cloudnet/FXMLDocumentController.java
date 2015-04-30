/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudnet;

import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class FXMLDocumentController implements Initializable {

    static String accessToken;

    @FXML
    Button button, activateButton;

    @FXML
    AnchorPane content;

    @FXML
    AnchorPane anc;

    @FXML
    TextField tokenTextField;

    @FXML
    ListView list, fileList;

    CloudNet cloud = new CloudNet();
    final String keyForApp = "3arl279eij5125u";
    final String secretKeyForApp = "ic83wodtpty04ut";
    private DbxClient dd;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void loginButtonPressed() throws IOException, DbxException {
        cloud.goToNextScreenTest();
    }

    @FXML
    public void addCloud() throws IOException, DbxException {
        final String authCode;

        Runnable run = new Runnable() {

            @Override
            public void run() {
                try {
                    activateButton.setVisible(true);
                    tokenTextField.setVisible(true);
                    getAccessToken();
                } catch (IOException | DbxException ex) {
                    System.out.println("There was an error getting the access token : " + ex);
                }
            }
        };
        Thread thr = new Thread(run);
        thr.setDaemon(true); //so when the main program ends, so will this thread.
        thr.start();

    }

    public void getAccessToken() throws IOException, DbxException {
        dd = cloud.setUpDropbox(keyForApp, secretKeyForApp);
        if (dd != null) {
            System.out.println("success");
        } else {
            System.out.println("failure");
        }
    }

    public void showCloud() throws DbxException, IOException {

        if (tokenTextField.getText().trim().length() > 0) {
            accessToken = tokenTextField.getText();
            DbxAuthFinish finish = cloud.auth.finish(accessToken);
            String token = finish.accessToken;
            DbxClient cliee = new DbxClient(cloud.conf, token);

            final DbxEntry.WithChildren file = cliee.getMetadataWithChildren("/");
            DbxEntry folders = cliee.getMetadata("/");

            //add folders to an arraylist to display
            
            //ArrayList<DbxEntry> listOfFiles = new ArrayList();
            ArrayList<String> listOfFiles2 = new ArrayList();

            for (DbxEntry c : file.children) {
                //listOfFiles.add(c);
                listOfFiles2.add(c.name);
            }

            ObservableList<String> itemss = FXCollections.observableArrayList();
            itemss.addAll(listOfFiles2);
            list.setItems(itemss);
            tokenTextField.clear();

            list.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    if (t.getClickCount() == 2) {
                        System.out.println("The clicked item was : " + list.getSelectionModel().getSelectedItem());
                    } else {
                        //if only clicked once then ignore it
                    }
                }
            });
        } else {
            //need to add error message here
            System.out.println("nothing entered");
        }
    }
    
    public void logout() throws IOException{
        cloud.logUserOut();
    }
}
