package smartHouse.objectClasses;


import java.util.Date;

public class Data {

    private double rawData;
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
