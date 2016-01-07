package smartHouse.resourceClasses;

import smartHouse.MainApplication.MainApp;
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
    public Response createUser(User user, int houseID, String hash) {
        String serverHash = HashHelper.hashCreator(MainApp.secretKey, houseID);
        Response ret = null;

        if(serverHash.equals(hash)){
            try{
                DatabaseResource.queryDatabase("INSERT into user (user_name, email, password, admin, house_id) VALUES " +
                        "('" + user.getUserName() + "','" + user.getEmail() + "','" + user.getPassword() + "'" +
                        ",'" + user.getProfile() + "','" + houseID + "')");
                DatabaseResource.closeConnection();
                ret = Response.status(Response.Status.CREATED).entity("New Room created").build();
            }catch (NullPointerException ee){
                ret = Response.status(Response.Status.NO_CONTENT).entity("Server has gone away").build();
            }
        }
        else if(!serverHash.equals(hash)){
            ret = Response.status(Response.Status.FORBIDDEN).entity("You are not authorized").build();
        }

        return ret;
    }

    @Override
    public Response updateUser(User user, int userID) {
        boolean successful = DatabaseResource.updateDatabase("UPDATE user SET user_name='"+user.getUserName()+"', email='"+user.getEmail()+"'" +
                ", password='"+user.getPassword()+"', admin='"+user.getProfile()+"' WHERE user_id='"+userID+"'");
        Response.StatusType responseStatus = successful == true ? Response.Status.OK : Response.Status.BAD_REQUEST;
        DatabaseResource.closeConnection();
        return Response.status(responseStatus).entity(successful).build();
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
    public Response login(String userName, String password, int houseID, String hash){
        Response.StatusType status = Response.Status.UNAUTHORIZED;
        boolean authorisedUser = false;
        String serverHash = HashHelper.hashCreator(MainApp.secretKey, houseID);
        if(serverHash.equals(hash)){
            authorisedUser = Login.checkLogin(userName,password,houseID);
            status = authorisedUser == true ? Response.Status.OK : Response.Status.UNAUTHORIZED;
        }
        else if(!serverHash.equals(hash)){
            status = Response.Status.UNAUTHORIZED;
        }

        return Response.status(status).entity(authorisedUser).build();
    }

    @Override
    public Response createUserWeb(String userName, String password, String email, int houseID, String hash){
        String serverHash = HashHelper.hashCreator(MainApp.secretKey, houseID);
        Response.StatusType responseStatus = Response.Status.FORBIDDEN;

        if(serverHash.equals(hash)){
            DatabaseResource.queryToAddToDatabase("INSERT into user (user_name, email, password, admin, house_id) VALUES " +
                    "('" + userName + "','" + email + "','" + password + "'" +
                    ", 'USER','" + houseID + "')");
            DatabaseResource.closeConnection();
            responseStatus = Response.Status.CREATED;
        }
        else if(!serverHash.equals(hash)){
            responseStatus = Response.Status.FORBIDDEN;
        }

        return Response
                .status(responseStatus)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .header("Access-Control-Max-Age", "1209600")
                .build();
    }
}
