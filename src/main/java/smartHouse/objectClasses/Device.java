package smartHouse.objectClasses;

/**
 * Created by Patrik Glendell on 02/10/15.
 */

import java.util.Collection;

public class Device {

    private int id;
    private String name;
    private Boolean status;
    private Collection<Data> Data;

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
    protected Collection<Data> getData() {
        return Data;
    }
    protected void setData(Collection<Data> data) {
        this.Data = data;
    }
}

