package smartHouse.objectModel;

import smartHouse.resourceModel.RoomResource;

import javax.ws.rs.core.Response;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Patrik Glendell on 02/10/15.
 */

public class Room implements RoomResource{
    private int id;
    private String name;
    private ConcurrentHashMap<Integer, Device> devices;
    private Environment environmentalData;

    protected int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected ConcurrentHashMap<Integer, Device> getDevices() {
        return devices;
    }

    protected void setDevices(ConcurrentHashMap<Integer, Device> devices) {
        this.devices = devices;
    }

    protected Environment getEnvironmentalData() {
        return environmentalData;
    }

    @Override
    public Response createRoom(Room newRoom) {
        return null;
    }

    @Override
    public Response updateRoom(int id) {
        return null;
    }

    @Override
    public ConcurrentHashMap<Integer, Room> getAllRooms() {
        return null;
    }

    @Override
    public Room getRoom(int id) {
        return null;
    }

    @Override
    public Response deleteRoom(int id) {
        return null;
    }
}
