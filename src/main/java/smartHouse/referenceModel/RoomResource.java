package smartHouse.referenceModel;

import smartHouse.objectModel.Room;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Patrik Glendell on 02/10/15.
 */

@Path("/House/{id}/Room")
public interface RoomResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    void createRoom(InputStream is);

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Room updateRoom(@PathParam("id") int id);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    ArrayList<Room> getAllRooms();

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Room getRoom(@PathParam("id") int id);

    @DELETE
    @Path("{id}")
    void deleteRoom(@PathParam("id") int id);



}
