package smartHouse.resourceClasses;

import com.sun.deploy.net.HttpRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import smartHouse.objectClasses.AdminRole;
import smartHouse.objectClasses.User;
import smartHouse.resourceInterfaces.UserResourceInterface;

import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Patrik Glendell on 05/10/15.
 */
@Path("/User")
public class UserResource implements UserResourceInterface {
    private Collection<User> users;
    private AtomicInteger counter;

    public UserResource(){
    }

    @Override
    public Response createUser(String userName, String email, String password, String adminRole, int houseID) {

        DatabaseResource.queryDatabase("INSERT into user (user_name, email, password, admin, house_id) VALUES " +
                "('"+userName+"','"+email+"','"+password+"','"+adminRole+"','"+houseID+"')");

        return Response.status(Response.Status.OK).entity("Great success").build();
    }

    @Override
    public Response updateUser(int id) {
        return null;
    }

    @Override
    public User getUser() {
        User user = new User();

        ResultSet userResult = DatabaseResource.queryDatabase("SELECT * FROM user WHERE user_id="+1);

        try{
            while(userResult.next()){
                user.setId(userResult.getInt("user_id"));
                user.setEmail(userResult.getString("email"));
                user.setProfile(userResult.getString("admin").equals("USER") ? AdminRole.USER:AdminRole.ADMIN);
                user.setUserName(userResult.getString("user_name"));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public Response deleteUser(int id) {
        return null;
    }
}
