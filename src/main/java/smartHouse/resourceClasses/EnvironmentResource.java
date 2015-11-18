package smartHouse.resourceClasses;

import java.sql.ResultSet;
import java.sql.SQLException;
import smartHouse.objectClasses.Environment;
import smartHouse.resourceInterfaces.EnvironmentResourceInterface;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;


/**
 * Created by Patrik Glendell on 05/10/15.
 *
 * Implemented by Pablo Cano
 */
@Path("/Environment")
public class EnvironmentResource implements EnvironmentResourceInterface {
    public EnvironmentResource(){}

    @Override
    public Response createEnvironment(Environment environment, int roomID) {
        DatabaseResource.queryDatabase("INSERT into environment (environment_name, environment_value, room_id) VALUES " +
                "('" + environment.getName() + "','" + environment.getValue() + "','" + roomID + "')");

        DatabaseResource.closeConnection();

        return Response.status(Response.Status.OK).entity("Great success").build();
    }

    @Override
    public Response updateEnvironment(int id, String value) {
        String error = DatabaseResource.updateDatabase("UPDATE device SET environment_value='"+value+"' WHERE environment_id="+id);
        DatabaseResource.closeConnection();
        return Response.status(Response.Status.OK).entity("You have updated the device, error = " +error).build();
    }

    @Override
    public Environment getEnvironment(int environmentID) {
        Environment environment = new Environment();
        ResultSet deviceResults = DatabaseResource.queryDatabase("SELECT * FROM environment WHERE environment_id ="+environmentID);

        try{
            while(deviceResults.next()){
                environment.setId(deviceResults.getInt("environment_id"));
                environment.setName(deviceResults.getString("environment_name"));
                environment.setValue(deviceResults.getString("environment_status"));
            }

        }catch (SQLException ee){
            ee.printStackTrace();
        }

        DatabaseResource.closeConnection();

        return environment;
    }

    @Override
    public Response deleteEnvironment(int id) {
        return null;
    }
}
