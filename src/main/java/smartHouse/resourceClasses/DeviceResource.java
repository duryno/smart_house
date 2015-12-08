package smartHouse.resourceClasses;

import smartHouse.objectClasses.Device;
import smartHouse.objectClasses.House;
import smartHouse.objectClasses.Room;
import smartHouse.resourceInterfaces.DeviceResourceInterface;

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
    public Response createDevice(Device device, int roomID) {
        DatabaseResource.queryDatabase("INSERT into device (device_name, device_status, room_id) VALUES " +
                "('" + device.getName() + "','" + device.getStatus() + "','" + roomID + "')");

        DatabaseResource.closeConnection();

        return Response.status(Response.Status.CREATED).entity("Great success").build();
    }

    @Override
    public Response updateDevice(int id, String status) {
        boolean successful = DatabaseResource.updateDatabase("UPDATE device SET device_status='"+status+"' WHERE device_id="+id);
        DatabaseResource.closeConnection();
        Response.StatusType responseStatus = successful == true ? Response.Status.OK : Response.Status.BAD_REQUEST;
        return Response.status(responseStatus).entity(successful).build();
    }

    @Override
    public Device getDevice(int deviceID) {
        Device device = new Device();
        ResultSet deviceResults = DatabaseResource.queryDatabase("SELECT * FROM device WHERE device_id ="+deviceID);

        try{
            while(deviceResults.next()){
                device.setId(deviceResults.getInt("device_id"));
                device.setName(deviceResults.getString("device_name"));
                device.setStatus(deviceResults.getString("device_status"));
            }

        }catch (SQLException ee){
            ee.printStackTrace();
        }

        DatabaseResource.closeConnection();

        return device;
    }

    @Override
    public Response deleteDevice(int id) {
        return null;
    }

    @Override
    public Response updateTemperature(int temp, int roomID){
        boolean successful = DatabaseResource.updateDatabase("UPDATE room SET room_temperature='"+temp+"' WHERE id="+roomID);
        Response.StatusType responseStatus = successful == true ? Response.Status.OK : Response.Status.BAD_REQUEST;
        DatabaseResource.closeConnection();
        return Response.status(responseStatus).entity(successful).build();
    }
}
