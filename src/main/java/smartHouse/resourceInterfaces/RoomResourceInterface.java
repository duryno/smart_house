package smartHouse.resourceInterfaces;

import org.json.JSONObject;
import smartHouse.objectClasses.Device;
import smartHouse.objectClasses.Room;
import smartHouse.resourceClasses.DeviceResource;
import smartHouse.resourceClasses.EnvironmentResource;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;

/**
 * Created by Patrik Glendell on 02/10/15.
 *
 * Implemented by David Munro & Juraj Orszag
 */

public interface RoomResourceInterface {

    @POST
    @Path("/createRoom/{houseID}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response createRoom(Room room, @PathParam("houseID") int houseID);

    @PUT
    @Path("/updateRoom/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateRoom(Room room, @PathParam("id") int roomID);

    @GET
    @Path("{roomID}")
    @Produces(MediaType.APPLICATION_JSON)
    Room getRoom(@PathParam("roomID") int roomID);

    @DELETE
    @Path("{id}")
    Response deleteRoom(@PathParam("id") int houseID);

    @GET
    @Path("/getAllDevices/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Collection<Device> getAllDevices(@PathParam("id") int roomID);

    @Path("{DeviceID : \\d+}/Device")
    default DeviceResource getDeviceResource() {
        return new DeviceResource();
    }
    @Path("Environment")
    default EnvironmentResource getEnvironmentResource() {
        return new EnvironmentResource();
    }


}
