package smartHouse.objectClasses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

/**
 * Created by Patrik Glendell on 02/10/15.
 */

@XmlRootElement
public class Environment {
    @XmlElement
    private int id;
    @XmlElementWrapper
    private Collection<Data> energyConsumption;
    @XmlElementWrapper
    private Collection<Data> waterConsumption;


    protected int getId() {
        return id;
    }
    protected Collection<Data> getEnergyConsumption() {
        return energyConsumption;
    }
    protected void setEnergyConsumption(Collection<Data> energyConsumption) {
        this.energyConsumption = energyConsumption;
    }
    protected Collection<Data> getWaterConsumption() {
        return waterConsumption;
    }
    protected void setWaterConsumption(Collection<Data> waterConsumption) {
        this.waterConsumption = waterConsumption;
    }

}
