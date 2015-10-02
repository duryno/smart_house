package smartHouse.referenceModel;

import smartHouse.objectModel.Device;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Created by Patrik Glendell on 02/10/15.
 */
@Path("House/{id}/Device")
public interface DeviceResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Device createDevice();

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Device updateDevice(@PathParam("id") int id);

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Device getDevice(@PathParam("id") int id);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    ArrayList<Device> getAllDevices();

    @DELETE
    @Path("{id}")
    void deleteDevice();
}
