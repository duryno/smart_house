package smartHouse.objectModel;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Rhino on 02/10/15.
 */
public class House {
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
