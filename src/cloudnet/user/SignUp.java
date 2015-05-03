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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Juraj
 */
public class SignUp {
    
    private static final String SIGN_UP_URL = "http://jorsino.com/cloudnet/register.php";
    
    public boolean createUser(String email, String username, String password) throws Exception {        
        URL url = new URL(SIGN_UP_URL);
        String urlParameters = "id=null&username="+username+"&password="+calculateHash(password)+"&email="+email;
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
        
        if(line == "1")
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
