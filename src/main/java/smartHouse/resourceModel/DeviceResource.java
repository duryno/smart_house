package smartHouse.resourceModel;

import smartHouse.objectModel.Device;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Patrik Glendell on 02/10/15.
 */
public interface DeviceResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response createDevice();

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateDevice(@PathParam("id") int id);

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Device getDevice(@PathParam("id") int id);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    ConcurrentHashMap<Integer,Device> getAllDevices();

    @DELETE
    @Path("{id}")
    Response deleteDevice(@PathParam("id") int id);
}
