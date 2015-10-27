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
 */

public interface UserResourceInterface {

    @POST
    @Path("/createUser/{userName}/{password}/{email}/{adminRole}/{houseID}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response createUser(@PathParam("userName") String userName,
                        @PathParam("email") String email,
                        @PathParam("password") String password,
                        @PathParam("adminRole") String adminRole,
                        @PathParam("houseID") int houseID);

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateUser(@PathParam("id") int id);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    User getUser();

    @DELETE
    @Path("{id}")
    Response deleteUser(@PathParam("id") int id);
}
