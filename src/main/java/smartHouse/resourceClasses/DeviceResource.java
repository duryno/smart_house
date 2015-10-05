package smartHouse.resourceClasses;

import smartHouse.objectClasses.Device;
import smartHouse.resourceInterfaces.DeviceResourceInterface;

import javax.ws.rs.core.Response;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Patrik Glendell on 05/10/15.
 */
public class DeviceResource implements DeviceResourceInterface {

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
