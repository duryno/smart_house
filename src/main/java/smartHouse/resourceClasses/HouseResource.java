package smartHouse.resourceClasses;

import smartHouse.objectClasses.AdminRole;
import smartHouse.objectClasses.House;
import smartHouse.objectClasses.Room;
import smartHouse.objectClasses.User;
import smartHouse.resourceInterfaces.HouseResourceInterface;

import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.ws.WebServiceException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Patrik Glendell on 05/10/15.
 */

@Path("/House")
public class HouseResource implements HouseResourceInterface {
    private Collection<House> houses;
    private AtomicInteger counter;

    public HouseResource() {
        try {
            counter = new AtomicInteger();
            houses = new ArrayList<>();
            ArrayList<User> users = new ArrayList<>();
            ArrayList<Room> rooms = new ArrayList<>();
            users.add(new User("PadaGle",
                    UriBuilder.fromPath("Patrik.Glendell@outlook.com").build(),
                    AdminRole.ADMIN));
            rooms.add(new Room(0,"LivingRoom"));
            rooms.add(new Room(1,"Bedroom"));
            rooms.add(new Room(2,"Toilet"));
            rooms.add(new Room(3,"Kitchen"));
            houses.add(new House(counter.getAndIncrement(),rooms,users));
        } catch (WebServiceException e) {throw new RuntimeException();}
    }

    @Override
    public Collection<House> getAllHouses() {
        try {
            return houses;
        } catch (WebServiceException | WebApplicationException ex) {
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
    public Response createHouse(UriInfo uri) {
        try {
            int incrementedCounter = counter.getAndIncrement();
            houses.add(new House(incrementedCounter, new ArrayList<>(), new ArrayList<>()));
            return Response.created(URI.create(uri.getBaseUri().toASCIIString() + incrementedCounter)).build();
        }catch (Exception e) {
            return Response.serverError().build();
        }
    }

}
