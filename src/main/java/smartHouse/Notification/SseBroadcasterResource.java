package smartHouse.Notification;

import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.glassfish.jersey.media.sse.SseFeature;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.net.URL;

/**
 * Created by Patrik Glendell on 18/12/15.
 */
    @Singleton
    @Path("broadcast")
    public class SseBroadcasterResource {

        private SseBroadcaster broadcaster = new SseBroadcaster();

        public URL broadcastUpdate(URL message) {
            OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
            OutboundEvent event = eventBuilder.name("message")
                    .mediaType(MediaType.APPLICATION_JSON_TYPE)
                    .data(URL.class, message)
                    .build();

            broadcaster.broadcast(event);

            return message;
        }

        @GET
        @Produces(SseFeature.SERVER_SENT_EVENTS)
        public EventOutput listenToBroadcast() {
            final EventOutput eventOutput = new EventOutput();
            this.broadcaster.add(eventOutput);
            return eventOutput;
        }
    }
