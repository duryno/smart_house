package smartHouse.resourceModel;

import smartHouse.objectModel.Device;
import smartHouse.objectModel.Environment;
import smartHouse.objectModel.Room;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Patrik Glendell on 02/10/15.
 */

public interface RoomResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response createRoom(Room newRoom);

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateRoom(@PathParam("id") int id);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    ConcurrentHashMap<Integer,Room> getAllRooms();

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Room getRoom(@PathParam("id") int id);

    @DELETE
    @Path("{id}")
    Response deleteRoom(@PathParam("id") int id);

    @Path("{DeviceID : \\d+}/Device")
    default Device getDevice() {
        return new Device();
    }
    @Path("Environment")
    default Environment getEnvironment() {
        return new Environment();
    }


}
