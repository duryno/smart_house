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
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

/**
 *
 * @author Juraj
 */
public class CloudNet extends Application {
    
    private static String userInput;
    private static String authCode;
    private static DbxClient cli;
    static CloudNet proj = new CloudNet();
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
//        URL url = new URL("http://jorsino.com/cloudnet/login.php");
//        //String urlParameters = "id="+"null"+"&username="+"juraj"+"&password="+"pass";
//        String urlParameters = "username="+"juraj"+"&password="+"pass";
//        HttpURLConnection hp=(HttpURLConnection)url.openConnection();
//        hp.setDoInput(true);
//        hp.setDoOutput(true);
//        hp.setInstanceFollowRedirects(false);
//        hp.setRequestMethod("POST");
//        hp.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
//        hp.setRequestProperty("charset", "utf-8");
//        hp.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
//        hp.setUseCaches (false);
//        DataOutputStream wr = new DataOutputStream(hp.getOutputStream ());
//        wr.writeBytes(urlParameters);
//        wr.flush();
//        String line;
//        BufferedReader reader = new BufferedReader(new InputStreamReader(hp.getInputStream()));
//        while(reader.ready()) {
//            line = reader.readLine();
//            System.out.println(line);
//        }
//        wr.close();
//        reader.close();
//        hp.disconnect();
        
        
//        final String keyForApp = "3arl279eij5125u";
//        final String secretKeyForApp = "ic83wodtpty04ut";
//        
//        //proj.test();
//        
//        cli = proj.setUpDropbox(keyForApp, secretKeyForApp);      
//        
//        System.out.println("Enter 'add' to add folder, enter 'delete' to delete folder");
//        userInput = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
        //proj.dropBoxCaller(userInput, cli);
    }
    
    private DbxClient setUpDropbox(String key, String secret) throws IOException, DbxException{
        
        DbxAppInfo apps = new DbxAppInfo(key, secret);
        DbxRequestConfig con = new DbxRequestConfig("ProjectTestingDavid/1.0", Locale.getDefault().toString());
        DbxWebAuthNoRedirect auth = new DbxWebAuthNoRedirect(con, apps);        
        String authoriseAccess = auth.start();
        System.out.println("Enter this URL in browser: " + authoriseAccess);
        System.out.println("Copy the authorization code: ");
        authCode = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
        DbxAuthFinish finish = auth.finish(authCode);
        String token = finish.accessToken;
        DbxClient client = new DbxClient(con, token);
        System.out.println(client.getAccountInfo());
        DbxEntry.WithChildren file = client.getMetadataWithChildren("/");
             
        System.out.println("Files: ");
        for(DbxEntry c: file.children){
            System.out.println("    " +c.name +"    " +c.toString());
        }
                
        return client;        
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
