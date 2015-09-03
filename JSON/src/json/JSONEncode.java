/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author davidmunro
 */
public class JSONEncode {

    public JSONEncode() {
        
    }

    public static JSONArray encode() {
        JSONObject liverpool = new JSONObject();
        liverpool.put("Club", "Liverpool");
        liverpool.put("Manager", "Brendan Rodgers");
        
        JSONObject norwich = new JSONObject();
        norwich.put("Club", "Norwich");
        norwich.put("Manager", "Alex O'neil");

        JSONArray norwichDetails = new JSONArray();
        norwichDetails.add("Stadium size : 28500");
        norwichDetails.add("Founded : 1905");
        norwichDetails.add("Club colour : Yellow & Green");
        
        norwich.put("Norwich Details", norwichDetails);

        JSONArray liverpoolDetails = new JSONArray();
        liverpoolDetails.add("Stadium size : 44500");
        liverpoolDetails.add("Founded : 1890");
        liverpoolDetails.add("Club colour : Red");

        liverpool.put("Liverpool details", liverpoolDetails);
        
        JSONArray clubs = new JSONArray();
        clubs.add(liverpool);
        clubs.add(norwich);

        System.out.println(clubs.toJSONString());
        
        return clubs;
    }
    
}
