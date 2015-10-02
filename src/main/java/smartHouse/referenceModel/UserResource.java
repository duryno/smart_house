package smartHouse.referenceModel;

import smartHouse.objectModel.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;


/**
 * Created by Patrik Glendell on 02/10/15.
 */

@Path("House/{id}/User")
public interface UserResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    User createUser();

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    User updateUser(@PathParam("id") int id);

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    User getUser(@PathParam("id") int id);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    ArrayList<User> getAllUsers();

    @DELETE
    @Path("{id}")
    void deleteUser(@PathParam("id") int id);
}
