package smartHouse.resourceInterfaces;

import smartHouse.objectClasses.House;
import smartHouse.objectClasses.Room;
import smartHouse.objectClasses.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

/**
 * Created by Patrik Glendell on 02/10/15.
 */
public interface HouseResourceInterface {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Collection<House> getAllHouses();   //JSON list of all house uri's

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    House getHouse(@PathParam("id") int id);     // Get house in json format

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateHouse(@PathParam("id") int id); // update specific house

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response createHouse(@PathParam("id") int id);    // create a new house and append it

    @Path("{HouseID : \\d+}/User")
    default User getUser() {
        return new User();
    }
    @Path("{HouseID : \\d+}/Room")
    default Room getRoom() {
        return new Room();
    }


}
