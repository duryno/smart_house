package smartHouse.resourceClasses;

import smartHouse.MainApplication.MainApp;
import smartHouse.objectClasses.AdminRole;
import smartHouse.objectClasses.Device;
import smartHouse.objectClasses.House;
import smartHouse.objectClasses.Room;
import smartHouse.resourceInterfaces.DeviceResourceInterface;
import sun.applet.Main;

import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

/**
 * Created by Patrik Glendell on 05/10/15.
 *
 * Implemented by David Munro & Juraj Orszag
 */
@Path("/Device")
public class DeviceResource implements DeviceResourceInterface {

    public DeviceResource(){}

    @Override
    public Response createDevice(Device device, int roomID, int houseID, String hash) {
        String serverHash = HashHelper.hashCreator(MainApp.secretKey, houseID);
        Response ret = null;

        if(serverHash.equals(hash)){
            try{
                DatabaseResource.queryToAddToDatabase("INSERT into device (device_name, device_status, room_id) VALUES " +
                        "('" + device.getName() + "','" + device.getStatus() + "','" + roomID + "')");
                DatabaseResource.closeConnection();
                ret = Response.status(Response.Status.CREATED).entity("New Device created").build();
            }catch (Exception ee){
                ret = Response.status(Response.Status.BAD_REQUEST).entity("Server has gone away").build();
            }
        }
        else if(!serverHash.equals(hash)){
            ret = Response.status(Response.Status.FORBIDDEN).entity("You are not authorized").build();
        }

        return ret;
    }

    @Override
    public Response updateDevice(int id, String status, int houseID, String hash) {
        String serverHash = HashHelper.hashCreator(MainApp.secretKey, houseID);
        Response.StatusType responseStatus = Response.Status.BAD_REQUEST;
        boolean successful = false;

        if(serverHash.equals(hash)){
            try{
                successful = DatabaseResource.updateDatabase("UPDATE device SET device_status='"+status+"' WHERE device_id="+id);
                DatabaseResource.closeConnection();
                responseStatus = successful == true ? Response.Status.OK : Response.Status.BAD_REQUEST;
            }catch(Exception ee){
                responseStatus = Response.Status.BAD_REQUEST;
            }
        }
        else if(!serverHash.equals(hash)){
            responseStatus = Response.Status.FORBIDDEN;
        }
        return Response.status(responseStatus).entity(successful).build();
    }

    @Override
    public Device getDevice(int deviceID, int houseID, String hash) {
        String serverHash = HashHelper.hashCreator(MainApp.secretKey, houseID);
        Device device = null;
        if(serverHash.equals(hash)){
            try{
                device = new Device();
                ResultSet deviceResults = DatabaseResource.queryDatabase("SELECT * FROM device WHERE device_id ="+deviceID);

                while(deviceResults.next()){
                    device.setId(deviceResults.getInt("device_id"));
                    device.setName(deviceResults.getString("device_name"));
                    device.setStatus(deviceResults.getString("device_status"));
                }

                DatabaseResource.closeConnection();
            }catch (Exception ee){
                device = null;
            }
        }
        else if(!serverHash.equals(hash)){
            device = null;
        }
        return device;
    }

    @Override
    public Response deleteDevice(int id, String role ,int houseID, String hash) {
        String serverHash = HashHelper.hashCreator(MainApp.secretKey, houseID);
        Response response = null;

        /*if(serverHash.equals(hash)){
            if(AdminRole.ADMIN.compareTo(role) == 0){
                response = Response.status(Response.Status.OK).entity("Role was admin").build();
            }
            else{
                response = Response.status(Response.Status.UNAUTHORIZED).entity("Role was not admin").build();
            }
        }
        else if(!serverHash.equals(hash)){
            response = Response.status(Response.Status.FORBIDDEN).entity("You are not authorized").build();
        }
        */

        if(serverHash.equals(hash)){
            if(role.equals("ADMIN")){
                response = Response.status(Response.Status.OK).entity("Role was admin").build();
            }
            else{
                response = Response.status(Response.Status.UNAUTHORIZED).entity("Role was not admin").build();
            }
        }
        else if(!serverHash.equals(hash)){
            response = Response.status(Response.Status.FORBIDDEN).entity("You are not authorized").build();
        }

        return response;
    }

    @Override
    public Response updateTemperature(int temp, int roomID, int houseID, String hash){
        String serverHash = HashHelper.hashCreator(MainApp.secretKey, houseID);
        Response.StatusType responseStatus = Response.Status.BAD_REQUEST;
        boolean successful = false;
        if(serverHash.equals(hash)){
            try{
                successful = DatabaseResource.updateDatabase("UPDATE room SET room_temperature='"+temp+"' WHERE id="+roomID);
                responseStatus = Response.Status.OK;
            }catch(Exception ee){
                responseStatus = Response.Status.NO_CONTENT;
            }
        }
        else if(serverHash.equals(hash)){
            responseStatus = Response.Status.FORBIDDEN;
        }
        return Response.status(responseStatus).entity(successful).build();
    }
}
