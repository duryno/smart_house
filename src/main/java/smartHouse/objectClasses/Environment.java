package smartHouse.objectClasses;

import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Patrik Glendell on 02/10/15.
 * 
 * Implemented by Pablo Cano
 */

@XmlRootElement(name = "Environment")
@XmlAccessorType(XmlAccessType.FIELD)
public class Environment {

    @XmlAttribute
    private int id;
    
    @XmlElement
    private String name;
    
    @XmlElement
    private String value;

    @XmlList
    private Collection<Data> Data;

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
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public Collection<Data> getConsumption() {
        return Data;
    }
    public void setConsumption(Collection<Data> data) {
        this.Data = data;
    }
}
