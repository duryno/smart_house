package smartHouse.MainApplication;

import smartHouse.resourceClasses.*;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Patrik Glendell on 05/10/15.
 */
public class MainApp extends Application {

    private Set<Object> singletons = new HashSet<>();
    private Set<Class<?>> perRequestResources = new HashSet<>();

    public MainApp() {

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
