package smartHouse.resourceClasses;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import smartHouse.objectClasses.Device;
import smartHouse.objectClasses.Room;
import smartHouse.resourceInterfaces.RoomResourceInterface;

import javax.json.JsonObject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Patrik Glendell on 05/10/15.
 *
 * Author David Munro & Juraj Orszag
 */
@Path("/Room")
public class RoomResource implements RoomResourceInterface{

    @Override
    public Response createRoom(String roomName, int temperature, int waterConsumption, int energyConsumption, int houseID) {
        DatabaseResource.queryDatabase("INSERT into room (room_name, room_temperature, room_water_consumption, "+
                "room_energy_consumption, house_id) VALUES "+
                "('"+roomName+"','"+temperature+"','"+waterConsumption+"','"+energyConsumption+"','"+houseID+"')");

        return Response.status(Response.Status.OK).entity("great success").build();
    }

    @Override
    public Response updateRoom(int id) {
        return null;
    }

    @Override
    public Room getRoom(int id) {
        Room room = new Room();
        List <Device> devices;
        ResultSet roomResults = DatabaseResource.queryDatabase("SELECT * FROM room WHERE id=" + id);

        try {
            while(roomResults.next()){
                room.setId(roomResults.getInt("id"));
                room.setName(roomResults.getString("room_name"));
                room.setTemperature(roomResults.getInt("room_temperature"));
                room.setWaterConsumption(roomResults.getInt("room_water_consumption"));
                room.setEnergyConsumption(roomResults.getInt("room_energy_consumption"));
            }

            roomResults.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        ResultSet deviceResult = DatabaseResource.queryDatabase("SELECT * FROM device WHERE room_id="+id);
        try {

            devices = new ArrayList<>();
            ResultSet deviceCount = DatabaseResource.queryDatabase("SELECT COUNT(*) FROM device");
            //int count = deviceCount.getInt(1);
            int count = 0;

            while (deviceResult.next()) {
                count++;
                for (int i = 0; i < count; i++) {
                    Device device = new Device();
                    device.setId(deviceResult.getInt("device_id"));
                    device.setName(deviceResult.getString("device_name"));
                    device.setStatus(deviceResult.getString("device_status"));
                    devices.add(device);
                }
            }

            room.setDevices(devices);
            deviceResult.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DatabaseResource.closeConnection();

        return room;
    }

    @Override
    public Response deleteRoom(int id) {
        return null;
    }

    @Override
    public Collection<Device> getAllDevices() {
        return null;
    }
}
