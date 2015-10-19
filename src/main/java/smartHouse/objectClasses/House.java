package smartHouse.objectClasses;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * Created by Patrik Glendell on 02/10/15.
 */
@XmlRootElement(name = "House")
@XmlAccessorType(XmlAccessType.FIELD)
public class House {
    @XmlAttribute
    private int id;
    @XmlList
    private Collection<Room> rooms;
    @XmlList
    private Collection<User> users;

    public House(int id, Collection<Room> rooms, Collection<User> users){
        this.id = id;
        this.setRooms(rooms);
        this.setUsers(users);
    }
    public House(int id) {
        this.id = id;
        this.setRooms(new ArrayList<>());
        this.setUsers(new ArrayList<>());
    }
    public House() {
        this.id = new Random(100000).nextInt();
        this.setRooms(new ArrayList<>());
        this.setUsers(new ArrayList<>());
    }

    public Collection<Room> getRooms() {
        return rooms;
    }
    public void setRooms(Collection<Room> rooms) {
        this.rooms = rooms;
    }
    public Collection<User> getUsers() {
        return users;
    }
    public void setUsers(Collection<User> users) {
        this.users = users;
    }
    public int getId() {
        return id;
    }
}
