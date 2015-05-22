/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cloudnet.user;

import cloudnet.FXMLDocumentController;
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
    private FXMLDocumentController controller = new FXMLDocumentController();

    public boolean checkLogin(String username, String password) {
        String urlParameters = "username=" + username + "&password=" + calculateHash(password);
        if (!username.isEmpty() && !password.isEmpty()) {
            return sendToDatabase(urlParameters, LOGIN_URL);
        } else {
            return false;
        }
    }

    public boolean createUser(String email, String username, String password) {
        String urlParameters = "id=null&username=" + username
                + "&password=" + calculateHash(password) + "&email=" + email;
        if (!email.isEmpty() && !username.isEmpty() && !password.isEmpty()) {
            return sendToDatabase(urlParameters, SIGN_UP_URL);
        } else {
            return false;
        }
    }

    private boolean sendToDatabase(String parameters, String address) {
        String line = null;
        String dropBoxToken = null;
        String oneDriveToken = null;
        String google_driveToken = null;
        boolean allowedAccess = false;

        try {
            URL url = new URL(address);
            HttpURLConnection hp = (HttpURLConnection) url.openConnection();
            hp.setDoInput(true);
            hp.setDoOutput(true);
            hp.setInstanceFollowRedirects(false);
            hp.setRequestMethod("POST");
            hp.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            hp.setRequestProperty("charset", "utf-8");
            hp.setRequestProperty("Content-Length", "" + Integer.toString(parameters.getBytes().length));
            hp.setUseCaches(false);
            DataOutputStream wr = new DataOutputStream(hp.getOutputStream());
            wr.writeBytes(parameters);
            wr.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(hp.getInputStream()));
            
            String id = reader.readLine();  
            if(id.equals("00")) {
                allowedAccess = false;
                controller.handleNoUser();
            } else {
                allowedAccess = true;
                User.setID(id);
            }

            while (reader.ready()) {
                line = reader.readLine();

                if (line.startsWith("dropbox")) {
                    dropBoxToken = line.substring(8, line.length());
                } else if (line.startsWith("box")) {
                    oneDriveToken = line.substring(4, line.length());
                } else if (line.startsWith("google_drive")) {
                    google_driveToken = line.substring(13, line.length());
                }
            }

            System.out.println("Dropbox token " + dropBoxToken);
            System.out.println("OneDrive token " + oneDriveToken);
            System.out.println("GoogleDrive token " + google_driveToken);
            User.setDropboxToken(String.valueOf(dropBoxToken));
            User.setBoxToken(String.valueOf(oneDriveToken));
            User.setGoogleToken(String.valueOf(google_driveToken));

//            if (allowedAccess) {
//                line = "1";
//            } else {
//                line = "0";
//                controller.handleNoUser();
//            }

            wr.close();
            reader.close();
            hp.disconnect();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return allowedAccess;
    }

//    private boolean checkResult(String result) {
//        if(result.equals("00")) {
//            controller.handleNoUser();
//             return false;
//        }           
//        else 
//            return true;
//    }

    private String calculateHash(String password) {
        StringBuffer hash = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
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
