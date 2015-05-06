/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cloudnet.user;

/**
 *
 * @author Juraj
 */
public class User {
    
    private static String id;
    
    public User(String id) {
        this.id = id;
    }
    
    public static String getId() {
        return id;
    }
    
}
