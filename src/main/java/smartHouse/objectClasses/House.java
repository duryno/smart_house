package smartHouse.objectClasses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Patrik Glendell on 02/10/15.
 */
@XmlRootElement
public class House {
    @XmlElement
    private int id;

    private ConcurrentHashMap<Integer, Room> rooms;
    private ConcurrentHashMap<Integer, User> users;

    public int getId() {
        return id;
    }
    public ConcurrentHashMap<Integer, Room> getRooms() {
        return rooms;
    }
    public ConcurrentHashMap<Integer, User> getUsers() {
        return users;
    }
}
