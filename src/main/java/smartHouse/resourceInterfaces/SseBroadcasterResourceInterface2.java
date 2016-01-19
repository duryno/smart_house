/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package smartHouse.resourceInterfaces;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseFeature;

/**
 *
 * @author Juraj
 */
public interface SseBroadcasterResourceInterface2 {
    
    @POST
    @Path("/ssePost")
    @Consumes("text/plain")
    Response setEvent(String string);

    @GET
    @Path("/sseGet")
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    EventOutput getEvent();
    
}
