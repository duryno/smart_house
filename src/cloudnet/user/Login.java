/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cloudnet.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Juraj
 */
public class Login {
    
    private static final String LOGIN_URL = "http://jorsino.com/cloudnet/login.php";
    private static final String SIGN_UP_URL = "http://jorsino.com/cloudnet/register.php";   
            
    public boolean checkLogin(String username, String password)  {    
        String urlParameters = "username="+username+"&password="+calculateHash(password);  
        if(!username.isEmpty() && !password.isEmpty())
            return sendToDatabase(urlParameters, LOGIN_URL);
        else 
            return false;
    }
    
    public boolean createUser(String email, String username, String password) {
        String urlParameters = "id=null&username="+username+
                "&password="+calculateHash(password)+"&email="+email;
        if(!email.isEmpty() && !username.isEmpty() && !password.isEmpty())
            return sendToDatabase(urlParameters, SIGN_UP_URL);
        else return false;
    }
    
    private boolean sendToDatabase(String parameters, String address) {
        String line = null;
        try {            
            URL url = new URL(address);
            HttpURLConnection hp=(HttpURLConnection)url.openConnection();
            hp.setDoInput(true);
            hp.setDoOutput(true);
            hp.setInstanceFollowRedirects(false);
            hp.setRequestMethod("POST");
            hp.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
            hp.setRequestProperty("charset", "utf-8");
            hp.setRequestProperty("Content-Length", "" + Integer.toString(parameters.getBytes().length));
            hp.setUseCaches (false);
            DataOutputStream wr = new DataOutputStream(hp.getOutputStream ());
            wr.writeBytes(parameters);
            wr.flush();            
            BufferedReader reader = new BufferedReader(new InputStreamReader(hp.getInputStream()));
            while(reader.ready()) {
                line = reader.readLine();  
                System.out.println(line);
            }
            
            wr.close();
            reader.close();
            hp.disconnect();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
                
        return checkResult(line);
    }
    
    private boolean checkResult(String result) {
        if(result.equals("1"))
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