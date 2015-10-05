package smartHouse.resourceClasses;

import smartHouse.objectClasses.Environment;
import smartHouse.resourceInterfaces.EnvironmentResourceInterface;

import javax.ws.rs.core.Response;

/**
 * Created by Patrik Glendell on 05/10/15.
 */
public class EnvironmentResource implements EnvironmentResourceInterface {

    @Override
    public Response createEnvironment() {
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
