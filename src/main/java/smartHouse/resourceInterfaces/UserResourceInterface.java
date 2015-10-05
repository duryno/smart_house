package smartHouse.resourceInterfaces;

import smartHouse.objectClasses.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

/**
 * Created by Patrik Glendell on 02/10/15.
 */

public interface UserResourceInterface {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response createUser();

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateUser(@PathParam("id") int id);

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    User getUser(@PathParam("id") int id);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Collection<User> getAllUsers();

    @DELETE
    @Path("{id}")
    Response deleteUser(@PathParam("id") int id);
}
