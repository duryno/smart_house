package smartHouse.resourceClasses;

import smartHouse.objectClasses.Device;
import smartHouse.resourceInterfaces.DeviceResourceInterface;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;

/**
 * Created by Patrik Glendell on 05/10/15.
 */
public class DeviceResource implements DeviceResourceInterface {

    public DeviceResource(){}
    @Override
    public Response createDevice(@Context UriInfo uri) {
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
    public Collection<Device> getAllDevices() {
        return null;
    }

    @Override
    public Response deleteDevice(int id) {
        return null;
    }
}
