package smartHouse.objectClasses;

/**
 * Created by Patrik Glendell on 02/10/15.
 */

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement
public class Device {
    @XmlElement
    private int id;
    @XmlElement
    private String name;
    @XmlElement
    private Boolean status;
    @XmlElementWrapper
    private Collection<Data> Data;


    public Device() {
    }
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
    protected Boolean getStatus() {
        return status;
    }
    protected void setStatus(Boolean status) {
        this.status = status;
    }
    protected Collection<Data> getData() {
        return Data;
    }
    protected void setData(Collection<Data> data) {
        this.Data = data;
    }
}

