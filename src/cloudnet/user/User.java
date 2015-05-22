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
public abstract class User {

    private static String id;
    private static String dropboxToken;
    private static String googleToken;
    private static String boxToken;
    
    public static void setID(String userID){
        id = userID;
    }

    public static void setDropboxToken(String aDropboxToken) {
        dropboxToken = aDropboxToken;
    }

    public static void setGoogleToken(String aGoogleToken) {
        googleToken = aGoogleToken;
    }

    public static void setBoxToken(String aOneDriveToken) {
        boxToken = aOneDriveToken;
    }

    public static String getId() {
        return id;
    }

    public static String getDropboxToken() {
        return dropboxToken;
    }

    public static String getGoogleToken() {
        return googleToken;
    }

    public static String getBoxToken() {
        return boxToken;
    }

}
