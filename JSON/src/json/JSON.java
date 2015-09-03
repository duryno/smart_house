/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

/**
 *
 * @author davidmunro
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.simple.JSONObject;


public class JSON{

    public static void main(String[] args) {
        JSONDecode decode = new JSONDecode(JSONEncode.encode());
        decode.test();
        
        

    }


}
