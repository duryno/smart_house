package smartHouse.resourceClasses;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by davidmunro on 27/10/2015.
 */
public class Login {

    public static boolean checkLogin(String userName, String password, int houseID){
        boolean allowedAccess;
        String dbUserName ="";
        String dbPassword ="";
        ResultSet userCredential = DatabaseResource.queryDatabase("SELECT user_name, password FROM user WHERE house_id="+houseID);

        try {
            while(userCredential.next()){
                if(userName.equals(userCredential.getString("user_name"))){
                    dbUserName = userCredential.getString("user_name");
                }
                if(password.equals(userCredential.getString("password"))){
                    dbPassword = userCredential.getString("password");
                }
            }
        } catch (SQLException e) {
            e.getMessage();
        }

        allowedAccess = dbUserName.equals(userName) && dbPassword.equals(password);
        DatabaseResource.closeConnection();

        return allowedAccess;
    }
}
