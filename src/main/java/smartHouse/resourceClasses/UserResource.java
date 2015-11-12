package smartHouse.resourceClasses;

import smartHouse.objectClasses.AdminRole;
import smartHouse.objectClasses.User;
import smartHouse.resourceInterfaces.UserResourceInterface;

import javax.ws.rs.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ws.rs.core.Response;

/**
 * Created by Patrik Glendell on 05/10/15.
 *
 * Implemented by David Munro & Juraj Orszag
 */
@Path("/User")
public class UserResource implements UserResourceInterface {

    public UserResource(){
    }

    @Override
    public Response createUser(User user, int houseID) {
        DatabaseResource.queryDatabase("INSERT into user (user_name, email, password, admin, house_id) VALUES " +
                "('" + user.getUserName() + "','" + user.getEmail() + "','" + user.getPassword() + "'" +
                ",'" + user.getProfile() + "','" + houseID + "')");

        return Response.status(Response.Status.OK).entity("You created a User!").build();
    }

    @Override
    public Response updateUser(User user, int userID) {
        String error = DatabaseResource.updateDatabase("UPDATE user SET user_name='"+user.getUserName()+"', email='"+user.getEmail()+"'" +
                ", password='"+user.getPassword()+"', admin='"+user.getProfile()+"' WHERE user_id='"+userID+"'");

        return Response.status(Response.Status.OK).entity("User updated! Error = "+error).build();
    }

    @Override
    public User getUser(int id) {
        User user = new User();

        ResultSet userResult = DatabaseResource.queryDatabase("SELECT * FROM user WHERE user_id="+id);

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
        DatabaseResource.closeConnection();

        return user;
    }

    @Override
    public Response deleteUser(int id) {
        return null;
    }

    @Override
    public Response login(String userName, String password, int houseID){
        boolean authorisedUser = Login.checkLogin(userName,password,houseID);
        Response.StatusType status = authorisedUser == true ? Response.Status.OK : Response.Status.FORBIDDEN;

        return Response.status(status).entity(authorisedUser).build();
    }

    @Override
    public Response createUserWeb(String userName, String password, String email, int houseID){
        String error = DatabaseResource.queryToAddToDatabase("INSERT into user (user_name, email, password, admin, house_id) VALUES " +
                "('" + userName + "','" + email + "','" + password + "'" +
                ", USER,'" + houseID + "')");

        return Response.status(Response.Status.OK).entity("You created a User! Is there error..."+error).build();
    }
}
