package smartHouse.resourceModel;

import smartHouse.objectModel.Environment;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * Created by Patrik Glendell on 02/10/15.
 */

public interface EnvironmentResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response createEnvironment();

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateEnvironment();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Environment getEnvironment();

    @DELETE
    Response deleteEnvironment();
}
