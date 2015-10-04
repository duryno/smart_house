package smartHouse.objectModel;

import smartHouse.resourceModel.UserResource;

import javax.ws.rs.core.Response;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Patrik Glendell on 02/10/15.
 */
public class User implements UserResource{
    private int id;
    private String userName;
    private java.net.URI email;
    private AdminRole profile;

    protected int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    protected String getUserName() {
        return userName;
    }

    protected java.net.URI getEmail() {
        return email;
    }

    protected AdminRole getProfile() {
        return profile;
    }

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
    public ConcurrentHashMap<Integer, User> getAllUsers() {
        return null;
    }

    @Override
    public Response deleteUser(int id) {
        return null;
    }
}
