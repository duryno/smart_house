package smartHouse.resourceClasses;

import smartHouse.objectClasses.AdminRole;
import smartHouse.objectClasses.User;
import smartHouse.resourceInterfaces.UserResourceInterface;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Patrik Glendell on 05/10/15.
 */
public class UserResource implements UserResourceInterface {
    private Collection<User> users;
    private AtomicInteger counter;

    public UserResource(){
        // todo fetch user data from db
        users = new ArrayList<>();
        counter = new AtomicInteger();
        users.add(new User ("PadaGle",
                UriBuilder.fromPath("Patrik.Glendell@outlook.com").build(),
                AdminRole.ADMIN));
    }

    @Override
    public Response createUser(@Context UriInfo uri) {
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
