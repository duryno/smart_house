package smartHouse.monitoring;

import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Patrik Glendell on 17/11/15.
 */
// https://jersey.java.net/documentation/latest/monitoring_tracing.html

public class ServerEventListener implements ApplicationEventListener {
    private static final Logger log = Logger.getGlobal();
    private volatile int requestCnt = 0;

    @Override
    public void onEvent(ApplicationEvent event) {
        switch (event.getType()) {
            case INITIALIZATION_FINISHED:
                log.log(Level.INFO, "Application "
                        + event.getResourceConfig().getApplicationName()
                        + " was initialized.");
                break;
            case DESTROY_FINISHED:
                log.log(Level.INFO, "Application "
                        + event.getResourceConfig().getApplicationName() + " destroyed.");
                break;
            case RELOAD_FINISHED:
        }
    }

    @Override
    public RequestEventListener onRequest(RequestEvent requestEvent) {
        requestCnt++;
        log.log(Level.INFO, "Request " + requestCnt + " started.");
        // return the listener instance that will handle this request.
        return new ServerRequestEventListener(requestCnt, log);
    }
}

