package smartHouse.resourceClasses;

import smartHouse.objectClasses.House;
import smartHouse.resourceInterfaces.HouseResourceInterface;

import javax.ws.rs.core.Response;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Patrik Glendell on 05/10/15.
 */
public class HouseResource implements HouseResourceInterface{

    @Override
    public ConcurrentHashMap<Integer, House> getAllHouses() {
        return null;
    }

    @Override
    public House getHouse(int id) {
        return null;
    }

    @Override
    public Response updateHouse(int id) {
        return null;
    }

    @Override
    public Response createHouse(int id) {
        return null;
    }
}
