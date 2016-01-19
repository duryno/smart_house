package smartHouse.resourceInterfaces;

import smartHouse.objectClasses.Environment;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Patrik Glendell on 02/10/15.
 *
 * Implemented by David Munro & Juraj Orszag
 */

public interface EnvironmentResourceInterface {

    @POST
    @Path("/createEnvironment")
    @Consumes(MediaType.APPLICATION_JSON)
    Response createEnvironment(@Context UriInfo uri);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateEnvironment();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Environment getEnvironment();

    @DELETE
    Response deleteEnvironment();
}
