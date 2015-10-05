package smartHouse.resourceClasses;

import smartHouse.objectClasses.User;
import smartHouse.resourceInterfaces.UserResourceInterface;

import javax.ws.rs.core.Response;
import java.util.Collection;

/**
 * Created by Patrik Glendell on 05/10/15.
 */
public class UserResource implements UserResourceInterface {
    public UserResource(){}
    @Override
    public Response createUser() {
        return null;
    }
    @Override
    public Response updateUser(int id) {
        return null;
    }

    @Override
    public User getUser(int id) {
        return null;
    }

    @Override
    public Collection<User> getAllUsers() {
        return null;
    }

    @Override
    public Response deleteUser(int id) {
        return null;
    }
}
