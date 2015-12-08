package smartHouse.resourceClasses;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by davidmunro on 08/12/2015.
 */
public class HashHelper {

    public static String hashCreator(String secretKey, int houseID){
        String stringToHash = secretKey+String.valueOf(houseID);

        StringBuffer hash = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] pwd = stringToHash.getBytes();
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
