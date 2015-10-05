package smartHouse.objectClasses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

/**
 * Created by Patrik Glendell on 02/10/15.
 */
@XmlRootElement
public class House {
    @XmlElement
    private int id;
    @XmlElementWrapper
    private Collection<Room> rooms;
    @XmlElementWrapper
    private Collection<User> users;

    public int getId() {
        return id;
    }
    public Collection<Room> getRooms() {
        return rooms;
    }
    public Collection<User> getUsers() {
        return users;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRooms(Collection<Room> rooms) {
        this.rooms = rooms;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }
}
