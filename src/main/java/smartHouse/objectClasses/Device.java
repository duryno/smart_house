package smartHouse.objectClasses;

/**
 * Created by Patrik Glendell on 02/10/15.
 */

import java.util.Collection;

public class Device {

    private int id;
    private String name;
    private String status;
    private Collection<Data> Data;

    public Device() {
    }

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
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    protected Collection<Data> getData() {
        return Data;
    }
    protected void setData(Collection<Data> data) {
        this.Data = data;
    }
}

