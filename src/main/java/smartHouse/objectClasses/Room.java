package smartHouse.objectClasses;

import java.util.Collection;

/**
 * Created by Patrik Glendell on 02/10/15.
 */

public class Room {

    private int id;

    private String name;

    private Collection<Device> devices;

    private Environment environmentalData;


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
    protected Collection<Device> getDevices() {
        return devices;
    }
    protected void setDevices(Collection<Device> devices) {
        this.devices = devices;
    }
    protected Environment getEnvironmentalData() {
        return environmentalData;
    }
}
