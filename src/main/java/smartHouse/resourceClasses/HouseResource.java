package smartHouse.resourceClasses;

import smartHouse.objectClasses.AdminRole;
import smartHouse.objectClasses.House;
import smartHouse.objectClasses.Room;
import smartHouse.objectClasses.User;
import smartHouse.resourceInterfaces.HouseResourceInterface;

import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.transform.Result;
import javax.xml.ws.WebServiceException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.net.URI;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

/**
 * Created by Patrik Glendell on 05/10/15.
 *
 * Implemented by David Munro & Juraj Orszag
 */

@Path("/House")
public class HouseResource implements HouseResourceInterface {

    public HouseResource() {
    }

    @Override
    public Collection<House> getAllHouses() {
        return null;
    }

    @Override
    public House getHouse(int houseID) {
        ResultSet houseResults = DatabaseResource.queryDatabase("SELECT id, room_name FROM room WHERE house_id="+houseID);
        House house = new House();
        house.setId(houseID);
        ArrayList <Room> rooms = new ArrayList<>();
        try {
            while(houseResults.next()){
                Room room = new Room();
                room.setId(houseResults.getInt("id"));
                room.setName(houseResults.getString("room_name"));
                rooms.add(room);
            }
            house.setRooms(rooms);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return house;
    }

    @Override
    public Response updateHouse(int id) {
        return null;
    }

    @Override
    public Response createHouse(int id) {
        Response.StatusType status = Response.Status.FORBIDDEN;
        House house = null;
        try {

            DatabaseResource.queryToAddToDatabase("INSERT INTO house () VALUES ();");
            ResultSet resultSet = DatabaseResource.queryDatabase("SELECT id_house FROM house;");

            while (resultSet.next()) {
                try {
                    id = resultSet.getInt("id_house");
                    house = new House();
                    house.setId(id);
                } catch (SQLException ex) {
                    Logger.getLogger(HouseResource.class.getName()).log(SEVERE, null, ex);
                }
            }
            resultSet.close();

            status = Response.Status.OK;
        } catch (SQLException ex) {
            Logger.getLogger(HouseResource.class.getName()).log(SEVERE, null, ex);
        }

        DatabaseResource.closeConnection();

        return Response.status(status).entity("House id is - " + house.getId() + "\n").build();
    }

}
