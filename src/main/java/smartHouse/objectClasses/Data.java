package smartHouse.objectClasses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class Data {
    @XmlElement
    private double rawData;
    @XmlElement
    private Date timestamp;

    public Data(){}
    public double getRawData() {
        return rawData;
    }
    public void setRawData(double rawData) {
        this.rawData = rawData;
    }
    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
