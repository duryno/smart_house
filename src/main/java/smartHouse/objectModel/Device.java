package smartHouse.objectModel;

/**
 * Created by Patrik Glendell on 02/10/15.
 */

import smartHouse.resourceModel.DeviceResource;

import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class Device implements DeviceResource {
    private int id;
    private String name;
    private Boolean status;
    private ConcurrentHashMap<Date,Double> Data;

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

    protected ConcurrentHashMap<Date,Double> getData() {
        return Data;
    }

    protected void setData(ConcurrentHashMap<Date,Double> data) {
        Data = data;
    }

    @Override
    public Response createDevice() {
        return null;
    }

    @Override
    public Response updateDevice(int id) {
        return null;
    }

    @Override
    public Device getDevice(int id) {
        return null;
    }

    @Override
    public ConcurrentHashMap<Integer, Device> getAllDevices() {
        return null;
    }

    @Override
    public Response deleteDevice(int id) {
        return null;
    }
}
