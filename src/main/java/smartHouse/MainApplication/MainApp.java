package smartHouse.MainApplication;

import smartHouse.resourceClasses.*;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Patrik Glendell on 05/10/15.
 */
@ApplicationPath("/SmartHouse")
public class MainApp extends Application {

    private Set<Object> singletons = new HashSet<>();
    private Set<Class<?>> empty = new HashSet<>();

    public MainApp() {
        singletons.add(new HouseResource());
        singletons.add(new RoomResource());
        singletons.add(new UserResource());
        singletons.add(new DeviceResource());
        singletons.add(new EnvironmentResource());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }

    public Set<Class<?>> getEmpty() {
        return empty;
    }
}
