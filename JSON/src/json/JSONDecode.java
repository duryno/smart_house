/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author davidmunro
 */
public class JSONDecode {
    
    private final JSONArray array;
    
    public JSONDecode(JSONArray obj){
        array = obj;
    }
    
    public void test() {
        for (Object array1 : array) {
            System.out.println("value " + array1);
        }
        
        
        System.out.println();
    }
}
