/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloudnet;

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
import java.awt.Dialog;
import java.awt.Insets;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Locale;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;

/**
 *
 * @author Juraj
 */
public class CloudNet extends Application {

    private static String userInput;
    private static String authCode;
    private static DbxClient cli;
    static CloudNet proj = new CloudNet();
    static FXMLDocumentController con = new FXMLDocumentController();
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

//        URL url = new URL("http://jorsino.com/cloudnet/login.php");
//        //String urlParameters = "id="+"null"+"&username="+"juraj"+"&password="+"pass";
//        String urlParameters = "username=" + "juraj" + "&password=" + "pass";
//        HttpURLConnection hp = (HttpURLConnection) url.openConnection();
//        hp.setDoInput(true);
//        hp.setDoOutput(true);
//        hp.setInstanceFollowRedirects(false);
//        hp.setRequestMethod("POST");
//        hp.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//        hp.setRequestProperty("charset", "utf-8");
//        hp.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
//        hp.setUseCaches(false);
//        DataOutputStream wr = new DataOutputStream(hp.getOutputStream());
//        wr.writeBytes(urlParameters);
//        wr.flush();
//        String line;
//        BufferedReader reader = new BufferedReader(new InputStreamReader(hp.getInputStream()));
//        while (reader.ready()) {
//            line = reader.readLine();
//            System.out.println(line);
//        }
//        wr.close();
//        reader.close();
//        hp.disconnect();
    }

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

    public void goToNextScreenTest() throws IOException {
        FXMLLoader lo = new FXMLLoader(CloudNet.class.getResource("userHome.fxml"));

        root = FXMLLoader.load(getClass().getResource("userHome.fxml"));
        Scene sc = new Scene(root);
        stagey.setResizable(false);
        stagey.setOpacity(1);
        stagey.setTitle("Juraj's CloudNet");
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
