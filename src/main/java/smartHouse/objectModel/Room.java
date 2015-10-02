package smartHouse.objectModel;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Rhino on 02/10/15.
 */

public class Room {
    private int id;
    private String name;
    private ConcurrentHashMap<Integer, Device> devices;
    private Optional<Environment> environmentalData;

    protected int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected ConcurrentHashMap<Integer, Device> getDevices() {
        return devices;
    }

    protected void setDevices(ConcurrentHashMap<Integer, Device> devices) {
        this.devices = devices;
    }

    protected Optional<Environment> getEnvironmentalData() {
        return environmentalData;
    }
}
