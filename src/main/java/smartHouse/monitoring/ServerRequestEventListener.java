package smartHouse.monitoring;

import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Patrik Glendell on 17/11/15.
 */
public class ServerRequestEventListener implements RequestEventListener {
    private final int requestNumber;
    private final long startTime;
    private final Logger log;

    public ServerRequestEventListener(int requestCnt, Logger log) {
        this.requestNumber = requestCnt;
        this.log = log;
        startTime = System.currentTimeMillis();
    }

    @Override
    public void onEvent(RequestEvent event) {

        switch (event.getType()) {
            case RESOURCE_METHOD_START:
                log.log(Level.INFO, "Resource method "
                        + event.getUriInfo().getMatchedResourceMethod()
                        .getHttpMethod()
                        + " on resource " + event.getContainerRequest().getUriInfo().getMatchedResourceMethod()
                        + " started for request " + requestNumber
                        + " Header: " + event.getContainerRequest().getRequestHeaders() + "\n");
                break;
            case ON_EXCEPTION:
                log.log(Level.INFO, "Exception thrown: "
                        + event.getException().getStackTrace().toString()
                        + "\n because :  " + event.getExceptionCause().toString());
                break;

            case FINISHED:
                log.log(Level.INFO, "Request " + requestNumber
                        + " finished. Processing time "
                        + (System.currentTimeMillis() - startTime) + " ms.");
                if (event.getUriInfo().getMatchedResourceMethod().getHttpMethod().equalsIgnoreCase("post"))
                    break;
                break;
        }
    }


}
