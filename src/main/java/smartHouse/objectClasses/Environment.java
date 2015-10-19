package smartHouse.objectClasses;

import java.util.Collection;

/**
 * Created by Patrik Glendell on 02/10/15.
 */

public class Environment {

    private int id;
    private Collection<Data> energyConsumption;
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
