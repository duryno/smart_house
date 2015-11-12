package smartHouse.resourceClasses;

import smartHouse.objectClasses.Environment;
import smartHouse.resourceInterfaces.EnvironmentResourceInterface;

import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Patrik Glendell on 05/10/15.
 *
 * Implemented by David Munro & Juraj Orszag
 */
@Path("/Environment")
public class EnvironmentResource implements EnvironmentResourceInterface {
    public EnvironmentResource(){}

    @Override
    public Response createEnvironment(@Context UriInfo uri) {

        return null;
    }

    @Override
    public Response updateEnvironment() {
        return null;
    }

    @Override
    public Environment getEnvironment() {
        return null;
    }

    @Override
    public Response deleteEnvironment() {
        return null;
    }
}
