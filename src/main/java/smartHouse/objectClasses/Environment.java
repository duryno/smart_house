package smartHouse.objectClasses;

import java.util.Collection;

/**
 * Created by Patrik Glendell on 02/10/15.
 */

public class Environment {

    private int id;
    private Collection<Data> energyConsumption;
    private Collection<Data> waterConsumption;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Collection<Data> getEnergyConsumption() {
        return energyConsumption;
    }
    public void setEnergyConsumption(Collection<Data> energyConsumption) {
        this.energyConsumption = energyConsumption;
    }
    public Collection<Data> getWaterConsumption() {
        return waterConsumption;
    }
    public void setWaterConsumption(Collection<Data> waterConsumption) {
        this.waterConsumption = waterConsumption;
    }


}
