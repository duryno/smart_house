package smartHouse.resourceInterfaces;

import smartHouse.objectClasses.Device;

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
public interface DeviceResourceInterface {

    @POST
    @Path("/createDevice/{roomID}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response createDevice(Device device, @PathParam("roomID") int roomID);

    @PUT
    @Path("/{id}/{status}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateDevice(@PathParam("id") int id, @PathParam("status") String status);

    @GET
    @Path("{deviceID}")
    @Produces(MediaType.APPLICATION_JSON)
    Device getDevice(@PathParam("deviceID") int id);

    @DELETE
    @Path("{id}")
    Response deleteDevice(@PathParam("id") int id);
}
