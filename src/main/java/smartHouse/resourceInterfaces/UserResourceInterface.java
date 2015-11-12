package smartHouse.resourceInterfaces;

import smartHouse.objectClasses.User;

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

public interface UserResourceInterface {

    @POST
    @Path("/createUser/{houseID}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response createUser(User user, @PathParam("houseID") int houseId);

    @POST
    @Path("/createUser/{userName}/{password}/{email}/{houseID}")
    Response createUserWeb(@PathParam("userName") String userName,
                           @PathParam("password") String password,
                           @PathParam("email") String email,
                           @PathParam("houseID") int houseID);

    @PUT
    @Path("{userID}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateUser(User user, @PathParam("userID") int userID);

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    User getUser(@PathParam("id") int id);

    @DELETE
    @Path("{id}")
    Response deleteUser(@PathParam("id") int id);

    @GET
    @Path("/{userName}/{password}/{houseID}")
    Response login(@PathParam("userName") String userName,
                   @PathParam("password") String password,
                   @PathParam("houseID") int houseID);

}
