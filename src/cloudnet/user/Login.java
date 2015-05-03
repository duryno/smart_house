/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cloudnet.user;

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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 *
 * @author Juraj
 */
public class Login {
    
    private static final String LOGIN_URL = "http://jorsino.com/cloudnet/login.php";
    
    public boolean checkLogin(String username, String password) throws Exception {        
        URL url = new URL(LOGIN_URL);
        String urlParameters = "username="+username+"&password="+calculateHash(password);
        HttpURLConnection hp=(HttpURLConnection)url.openConnection();
        hp.setDoInput(true);
        hp.setDoOutput(true);
        hp.setInstanceFollowRedirects(false);
        hp.setRequestMethod("POST");
        hp.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
        hp.setRequestProperty("charset", "utf-8");
        hp.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
        hp.setUseCaches (false);
        DataOutputStream wr = new DataOutputStream(hp.getOutputStream ());
        wr.writeBytes(urlParameters);
        wr.flush();
        String line = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(hp.getInputStream()));
        while(reader.ready()) {
            line = reader.readLine();            
        }
        System.out.println(line);
        wr.close();
        reader.close();
        hp.disconnect();
        
        if(line.equals("1"))
            return true;
        else 
            return false;
    }
    
    private String calculateHash(String password) {
        StringBuffer hash = new StringBuffer();
        try {
            MessageDigest md=MessageDigest.getInstance("MD5");
            byte[] pwd = password.getBytes();
            md.update(pwd);
            byte[] hashVal = md.digest();
            for (int i = 0; i < hashVal.length; i++) {
                String hex = Integer.toHexString(0xFF & hashVal[i]);
                if (hex.length() == 1) {
                  hash.append('0');
                }
                hash.append(hex);
            }            
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } 
        return hash.toString();
    }
    
}
