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
 */
public interface DeviceResourceInterface {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response createDevice(@Context UriInfo uri);

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
    Collection<Device> getAllDevices();

    @DELETE
    @Path("{id}")
    Response deleteDevice(@PathParam("id") int id);
}
