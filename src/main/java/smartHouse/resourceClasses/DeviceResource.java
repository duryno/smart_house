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
 */
@Path("/Device")
public class DeviceResource implements DeviceResourceInterface {

    public DeviceResource(){}

    @Override
    public Response createDevice(String deviceName, String deviceStatus, int roomID) {
        DatabaseResource.queryDatabase("INSERT into device (device_name, device_status, room_id) VALUES " +
                "('" + deviceName + "','" + deviceStatus + "','" + roomID + "')");

        return Response.status(Response.Status.OK).entity("Great success").build();
    }

    @Override
    public Response updateDevice(int id) {
        return null;
    }

    @Override
    public Device getDevice(int id) {
        Device device = new Device();
        ResultSet deviceResults = DatabaseResource.queryDatabase("SELECT * FROM device WHERE device_id ="+id);

        try{
            device.setId(deviceResults.getInt("device_id"));
            device.setName(deviceResults.getString("device_name"));
            device.setStatus(deviceResults.getString("device_status"));

        }catch (SQLException ee){
            ee.printStackTrace();
        }

        return device;
    }

    @Override
    public Response deleteDevice(int id) {
        return null;
    }
}
