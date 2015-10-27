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
 */

@Path("/House")
public class HouseResource implements HouseResourceInterface {
    private Collection<House> houses;
    private AtomicInteger counter;

    public HouseResource() {
        try {
            counter = new AtomicInteger();
            houses = new ArrayList<>();
            ArrayList<User> users = new ArrayList<>();
            ArrayList<Room> rooms = new ArrayList<>();

            houses.add(new House(counter.getAndIncrement(), rooms, users));
        } catch (WebServiceException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Collection<House> getAllHouses() {
        try {
            return houses;
        } catch (WebServiceException | WebApplicationException ex) {
            return null;
        }
    }

    @Override
    public House getHouse(int id) {
        return houses.stream()
                .filter(house -> house.getId() == id)
                .reduce((head, tail) -> head)
                .orElse(null);
    }

    @Override
    public Response updateHouse(int id) {
        return null;
    }

    @Override
    public Response createHouse(UriInfo uri) {
        int id = 0;
        int status = 700;
        House house = null;

        try {

            DatabaseResource.queryToAddToDatabase("INSERT INTO house () VALUES ();");
            ResultSet resultSet = DatabaseResource.queryDatabase("SELECT id_house FROM house;");

            while (resultSet.next()) {
                try {
                    id = resultSet.getInt("id_house");
                    house = new House(id);
                } catch (SQLException ex) {
                    Logger.getLogger(HouseResource.class.getName()).log(SEVERE, null, ex);
                }
            }
            resultSet.close();

            status = 200;
        } catch (SQLException ex) {
            Logger.getLogger(HouseResource.class.getName()).log(SEVERE, null, ex);
        }

        DatabaseResource.closeConnection();

        //return json object on house?

        return Response.status(status).entity("House id is - " + house.getId() + "\n").build();
    }

}
