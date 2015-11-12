package smartHouse.resourceInterfaces;

import smartHouse.objectClasses.House;
import smartHouse.resourceClasses.RoomResource;
import smartHouse.resourceClasses.UserResource;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;

/**
 * Created by Patrik Glendell on 02/10/15.
 *
 * Implemented by David Munro & Juraj Orszag
 */
public interface HouseResourceInterface{
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Collection<House> getAllHouses();   //JSON list of all house uri's

    @GET
    @Path("/getHouse/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    House getHouse(@PathParam("id") int id);     // Get house in json format

    @PUT
    @Path("/updateHouse/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateHouse(@PathParam("id") int id); // update specific house

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response createHouse(int id);    // create a new house and append it

    @Path("{HouseID : \\d+}/User")
    default UserResource getUser() {
        return new UserResource();
    }
    @Path("{HouseID : \\d+}/Room")
    default RoomResource getRoom() {
        return new RoomResource();
    }


}
