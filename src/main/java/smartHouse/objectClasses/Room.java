package smartHouse.objectClasses;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Patrik Glendell on 02/10/15.
 */
@XmlRootElement(name = "Room")
@XmlAccessorType(XmlAccessType.FIELD)
public class Room {
    @XmlAttribute
    private int id;
    @XmlElement
    private String name;
    @XmlList
    private Collection<Device> devices;
    @XmlElement
    private Environment environmentalData;

    public Room(int id, String name) {
        setId(id);
        setName(name);
        setDevices(new ArrayList<>());
        this.environmentalData = new Environment();
    }
    public Room(){}


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Device> getDevices() {
        return devices;
    }

    public void setDevices(Collection<Device> devices) {
        this.devices = devices;
    }

    public Environment getEnvironmentalData() {
        return environmentalData;
    }

    public void setEnvironmentalData(Environment environmentalData) {
        this.environmentalData = environmentalData;
    }
}
