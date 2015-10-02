package smartHouse.referenceModel;

import smartHouse.objectModel.House;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Created by Patrik Glendell on 02/10/15.
 */
@Path("/House")
public interface HouseResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    ArrayList<House> getAllHouses();   //JSON list of all house uri's

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    House getHouse();     // Get house in json format

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    void updateHouse(@PathParam("id") int id); // update specific house

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    void createHouse();    // create a new house and append it

}
