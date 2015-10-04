package smartHouse.objectModel;

import smartHouse.resourceModel.EnvironmentResource;

import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Patrik Glendell on 02/10/15.
 */

public class Environment implements EnvironmentResource {
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

    @Override
    public Response createEnvironment() {
        return null;
    }

    @Override
    public Response updateEnvironment() {
        return null;
    }

    @Override
    public Environment getEnvironment() {
        return null;
    }

    @Override
    public Response deleteEnvironment() {
        return null;
    }
}
