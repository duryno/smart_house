package smartHouse.objectClasses;

/**
 * Created by Patrik Glendell on 02/10/15.
 */

import javax.xml.bind.annotation.*;
import java.util.Collection;

@XmlRootElement(name = "Device")
@XmlAccessorType(XmlAccessType.FIELD)
public class Device {

    @XmlAttribute
    private int id;
    @XmlElement
    private String name;
    @XmlElement
    private String status;
    @XmlList
    private Collection<Data> Data;

    public Device() {
    }

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
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Collection<Data> getData() {
        return Data;
    }
    public void setData(Collection<Data> data) {
        this.Data = data;
    }
}

