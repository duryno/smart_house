/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartHouse.Notification;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import smartHouse.resourceInterfaces.SseBroadcasterResourceInterface2;

/**
 *
 * @author Calle
 */
@Path("/broadcast")
public class SseBroadcasterResource2 implements SseBroadcasterResourceInterface2{
    
    private static SseBroadcaster broadcaster = new SseBroadcaster();

    @Override
    public EventOutput getEvent() {
        EventOutput eventOutput = new EventOutput();
        broadcaster.add(eventOutput);
        return eventOutput;
    }

    @Override
    public Response setEvent(String string) {
        OutboundEvent event = new OutboundEvent.Builder()
                .name("message")
                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                .data(string)
                .build();
        broadcaster.broadcast(event);
        return Response.status(Response.Status.OK).entity("OK").build();
    }
    
}
