package smartHouse.referenceModel;

import smartHouse.objectModel.Environment;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


/**
 * Created by Patrik Glendell on 02/10/15.
 */

@Path("House/{id}/Room/{id}/Environment")
public interface EnvironmentResource {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Environment createEnvironment();

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Environment updateEnvironment();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Environment getEnvironment();

    @DELETE
    void deleteEnvironment();
}
