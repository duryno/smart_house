package smartHouse.resourceClasses;

import smartHouse.objectClasses.House;
import smartHouse.objectClasses.Room;
import smartHouse.objectClasses.User;
import smartHouse.resourceInterfaces.HouseResourceInterface;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Patrik Glendell on 05/10/15.
 */

@Path("/House")
public class HouseResource implements HouseResourceInterface {
    private House house;
    private Collection<House> allHouses = new ArrayList<House>();

    public HouseResource() {
        house = new House();
        house.setId(1);
        house.setRooms(new ArrayList<Room>());
        house.setUsers(new ArrayList<User>());
        allHouses.add(house);
    }

    @Override
    public Collection<House> getAllHouses() {
        return allHouses;
    }

    @Override
    public House getHouse(int id) {
        return house;
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
