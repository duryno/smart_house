package smartHouse.objectClasses;

import java.util.Collection;
import java.util.Date;

/**
 * Created by Patrik Glendell on 02/10/15.
 */

public class Environment {
    private int id;
    private Collection<envData> energyConsumption;
    private Collection<envData> waterConsumption;

    protected int getId() {
        return id;
    }

    protected Collection<envData> getEnergyConsumption() {
        return energyConsumption;
    }

    protected void setEnergyConsumption(Collection<envData> energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

    protected Collection<envData> getWaterConsumption() {
        return waterConsumption;
    }

    protected void setWaterConsumption(Collection<envData> waterConsumption) {
        this.waterConsumption = waterConsumption;
    }

    public class envData {
        private Date date;
        private double value;

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }
}
