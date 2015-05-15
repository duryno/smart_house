/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cloudnet;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author Juraj
 */
public class DBConnector {
    
    public void sendToDatabase(String parameters, String address) {
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
            }
            System.out.println(line);
            wr.close();
            reader.close();
            hp.disconnect();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
                
    }
    
}
