package smartHouse.resourceClasses;

import smartHouse.objectClasses.Device;
import smartHouse.objectClasses.Room;
import smartHouse.resourceInterfaces.RoomResourceInterface;

import javax.ws.rs.core.Response;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by davidmunro on 12/11/2015.
 */
public class RoomResource implements RoomResourceInterface{

    @Override
    public Response createRoom(Room room, int houseID) {
        DatabaseResource.queryDatabase("INSERT into room (room_name, room_temperature, room_water_consumption, "+
                "room_energy_consumption, house_id) VALUES "+
                "('"+room.getName()+"','"+room.getTemperature()+"','"+room.getWaterConsumption()+"'" +
                ",'"+room.getEnergyConsumption()+"','"+houseID+"')");

        DatabaseResource.closeConnection();
        System.out.println("hwllo");

        return Response.status(Response.Status.OK).entity("great success").build();
    }

    @Override
    public Response updateRoom(Room room, int roomID) {
        DatabaseResource.queryDatabase("UPDATE room SET room_name='"+room.getName()+"', " +
                "room_temperature='"+room.getTemperature()+"', room_water_consumption='"+room.getWaterConsumption()+"'," +
                "room_energy_consumption='"+room.getEnergyConsumption()+"' WHERE room_id="+roomID);

        DatabaseResource.closeConnection();
        return Response.status(Response.Status.OK).entity("Room updated").build();
    }

    @Override
    public Room getRoom(int roomID) {
        Room room = new Room();
        List<Device> devices;
        ResultSet roomResults = DatabaseResource.queryDatabase("SELECT * FROM room WHERE id=" + roomID);
        //can we use the same result set for both queries?
        //ResultSet resultSet = null;
        //resultSet = DatabaseResource.queryDatabase("SELECT * FROM room WHERE id=" + roomID);

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

        //resultSet = DatabaseResource.queryDatabase("SELECT * FROM device WHERE room_id="+roomID);
        ResultSet deviceResult = DatabaseResource.queryDatabase("SELECT * FROM device WHERE room_id="+roomID);
        try {
            devices = new ArrayList<>();
            while (deviceResult.next()) {
                Device device = new Device();
                device.setId(deviceResult.getInt("device_id"));
                device.setName(deviceResult.getString("device_name"));
                device.setStatus(deviceResult.getString("device_status"));
                devices.add(device);
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
    public Response deleteRoom(int houseID) {
        return null;
    }

    @Override
    public Collection<Device> getAllDevices(int roomID) {

        return null;
    }

}
