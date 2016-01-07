package smartHouse.resourceInterfaces;

import smartHouse.objectClasses.Environment;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Patrik Glendell on 02/10/15.
 *
 * Implemented by Pablo Cano
 */

public interface EnvironmentResourceInterface {
    
    @POST
    @Path("/createEnvironment/{roomID}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response createEnvironment(Environment environment, @PathParam("roomID") int roomID);

    @PUT
    @Path("/{id}/{value}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateEnvironment(@PathParam("id") int id, @PathParam("value") String value);

    @GET
    @Path("{deviceID}")
    @Produces(MediaType.APPLICATION_JSON)
    Environment getEnvironment(@PathParam("environmentID") int id);

    @DELETE
    @Path("{id}")
    Response deleteEnvironment(@PathParam("id") int id);
}
