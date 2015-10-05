package smartHouse.objectClasses;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Patrik Glendell on 02/10/15.
 */

public class Environment {
    private int id;
    private ConcurrentHashMap<Date, Double> energyConsumption;
    private ConcurrentHashMap<Date, Double> waterConsumption;

    protected int getId() {
        return id;
    }

    protected ConcurrentHashMap<Date, Double> getEnergyConsumption() {
        return energyConsumption;
    }

    protected void setEnergyConsumption(ConcurrentHashMap<Date, Double> energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

    protected ConcurrentHashMap<Date, Double> getWaterConsumption() {
        return waterConsumption;
    }

    protected void setWaterConsumption(ConcurrentHashMap<Date, Double> waterConsumption) {
        this.waterConsumption = waterConsumption;
    }
}
