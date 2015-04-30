/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cloudnet.user;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Juraj
 */
public class SignUp {
    
    private static final String SIGN_UP_URL = "http://jorsino.com/cloudnet/register.php";
    
    public void createUser(String email, String username, String password) throws Exception {        
        URL url = new URL(SIGN_UP_URL);
        String urlParameters = "id=null&username="+username+"&password="+password+"&email="+email;
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
    }
    
}
