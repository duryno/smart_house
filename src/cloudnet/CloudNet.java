/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cloudnet;

import cloudnet.user.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.dropbox.core.*;
import java.awt.Desktop;
import java.net.URI;
import java.util.Locale;
import javafx.stage.Popup;

/**
 *
 * @author Juraj
 */
public class CloudNet extends Application {

    private static String authCode;
    static CloudNet proj = new CloudNet();
    
    public static FXMLDocumentController con = new FXMLDocumentController();
    public static UserHomeController homeControl = new UserHomeController();
    
    Scene scene;
    Parent root;
    public static Stage stagey;
    public DbxAppInfo apps;
    public DbxRequestConfig conf;
    public DbxWebAuthNoRedirect auth;

    @Override
    public void start(Stage stage) throws Exception {
        CloudNet.stagey = stage;

        root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
        
    }

    //check usage??
    public DbxClient setUpDropbox(String key, String secret) throws IOException, DbxException {

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
                e.printStackTrace();
            }
        }

        authCode = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
        DbxAuthFinish finish = auth.finish(authCode);
        String token = finish.accessToken;
        DbxClient client = new DbxClient(conf, token);

        return client;
    }

    public void goToNextScreen(String screen) throws IOException {
        FXMLLoader lo = new FXMLLoader(CloudNet.class.getResource(screen));
        lo.setController(homeControl);
        root = FXMLLoader.load(getClass().getResource(screen));
        Scene sc = new Scene(root);
        stagey.setResizable(false);
        stagey.setOpacity(1);
        stagey.setTitle("CloudNet");
        stagey.setScene(sc);
        stagey.show();
    }

    public void popUp(Popup po) {
        po.show(stagey);
    }

    public void logUserOut() throws IOException {
        root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Scene sc = new Scene(root);
        stagey.setResizable(false);
        stagey.setScene(sc);
        stagey.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
