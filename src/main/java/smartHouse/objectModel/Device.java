package smartHouse.objectModel;

/**
 * Created by Patrik Glendell on 02/10/15.
 */
import com.oracle.javafx.jmx.json.JSONDocument;
public class Device {
    private int id;
    private String name;
    private Boolean status;
    private JSONDocument Data;

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

    protected com.oracle.javafx.jmx.json.JSONDocument getData() {
        return Data;
    }

    protected void setData(com.oracle.javafx.jmx.json.JSONDocument data) {
        Data = data;
    }
}
