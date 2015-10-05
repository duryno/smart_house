package smartHouse.objectClasses;

/**
 * Created by Patrik Glendell on 02/10/15.
 */

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class Device {
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
}
