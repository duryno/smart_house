package smartHouse.resourceClasses;

import smartHouse.objectClasses.House;
import smartHouse.resourceInterfaces.HouseResourceInterface;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.xml.ws.WebServiceException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Patrik Glendell on 05/10/15.
 */

@Path("/House")
public class HouseResource implements HouseResourceInterface {

    private Collection<House> houses;

    public HouseResource() {
        try {
            houses = new ArrayList<>();
            houses.add(new House(1,new ArrayList<>(),new ArrayList<>()));
        } catch (WebServiceException e) {throw new RuntimeException();}
    }

    @Override
    public Collection<House> getAllHouses() {
        try {
            return houses;
        } catch (WebServiceException e) {
            return null;
        }
    }

    @Override
    public House getHouse(int id) {
        return houses.stream()
                .filter(house -> house.getId() == id)
                .reduce((head,tail) -> head)
                .orElse(null);
    }

    @Override
    public Response updateHouse(int id) {
        return null;
    }

    @Override
    public Response createHouse(@BeanParam House newHouse) {
        return null;
    }

}
