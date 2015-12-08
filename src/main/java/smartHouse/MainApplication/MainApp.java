package smartHouse.MainApplication;

import smartHouse.resourceClasses.*;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Patrik Glendell on 05/10/15.
 */
public class MainApp extends Application{

    private char [] chars = {'A','b','5','l','r','6','-','+','w','d','1','W','P','1'
            ,'?','&','C','*','*','=','U','<','a','.','D','J','U','4','^','@','m','o'};
    private Set<Object> singletons = new HashSet<>();
    private Set<Class<?>> perRequestResources = new HashSet<>();
    public static String secretKey = "";

    public MainApp() {
        for(int i = 0; i < chars.length; i++){
            switch (i){
                case 0:
                    secretKey += chars[4];
                    break;
                case 1:
                    secretKey += chars[13];
                    break;
                case 2:
                    secretKey += chars[17];
                    break;
                case 3:
                    secretKey += chars[2];
                    break;
                case 4:
                    secretKey += chars[22];
                    break;
                case 5:
                    secretKey += chars[1];
                    break;
                case 6:
                    secretKey += chars[19];
                    break;
                case 7:
                    secretKey += chars[27];
                    break;
                case 8:
                    secretKey += chars[3];
                    break;
                case 9:
                    secretKey += chars[15];
                    break;
                case 10:
                    secretKey += chars[8];
                    break;
                case 11:
                    secretKey += chars[24];
                    break;
                case 12:
                    secretKey += chars[5];
                    break;
                case 13:
                    secretKey += chars[20];
                    break;
                case 14:
                    secretKey += chars[11];
                    break;
                case 15:
                    secretKey += chars[23];
                    break;
                case 16:
                    secretKey += chars[0];
                    break;
                case 17:
                    secretKey += chars[7];
                    break;
                case 18:
                    secretKey += chars[26];
                    break;
                case 19:
                    secretKey += chars[18];
                    break;
                case 20:
                    secretKey += chars[31];
                    break;
                case 21:
                    secretKey += chars[6];
                    break;
                case 22:
                    secretKey += chars[12];
                    break;
                case 23:
                    secretKey += chars[30];
                    break;
                case 24:
                    secretKey += chars[9];
                    break;
                case 25:
                    secretKey += chars[25];
                    break;
                case 26:
                    secretKey += chars[29];
                    break;
                case 27:
                    secretKey += chars[16];
                    break;
                case 28:
                    secretKey += chars[10];
                    break;
                case 29:
                    secretKey += chars[21];
                    break;
                case 30:
                    secretKey += chars[28];
                    break;
                case 31:
                    secretKey += chars[14];
                    break;
            }


        }
    }

    @Override
    public Set<Object> getSingletons() {
        // configure JSON support and formatting for automatic marchalling and de-marchalling

        singletons.add(new HouseResource());
        singletons.add(new RoomResource());
        singletons.add(new UserResource());
        singletons.add(new DeviceResource());
        singletons.add(new EnvironmentResource());
        singletons.add(new org.glassfish.jersey.moxy.json.MoxyJsonFeature());
        singletons.add(new JsonMoxyConfigurationContextResolver());

        return singletons;
    }

    public Set<Class<?>> getEmpty() {
        //perRequestResources.add(org.glassfish.jersey.moxy.json.MoxyJsonFeature.class);
        //Configure Moxy behavior
        //perRequestResources.add(JsonMoxyConfigurationContextResolver.class);
        //perRequestResources.add(HouseResource.class);
        return perRequestResources;
    }
}
