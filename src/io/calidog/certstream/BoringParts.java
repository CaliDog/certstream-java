package io.calidog.certstream;

import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The class that takes care of ll the boring parts
 * of talking over WebSockets, most notably the onClose
 * which will get called if your internet connection
 * hiccups.
 */
public class BoringParts implements
        CertStreamErrorHandler,
        CertStreamCloseHandler,
        CertStreamOpenHandler {
    private static final Logger logger = LoggerFactory.getLogger(BoringParts.class);
    private boolean notClosed = true;

    //todo reconnection logic
    @Override
    public void onClose(int i, String s, boolean b) {
        logger.warn("OnClose was called wih i = " + i + ", s = "+s + ", b = b");
        System.out.println("OnClose was called wih i = " + i + ", s = "+s + ", b = b");

        notClosed = false;
    }

    @Override
    public void onError(Exception e) {
        System.out.println("Exception occurred: "+e.getMessage());
        e.printStackTrace();
        logger.debug("OnError was called for: " + e);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        StackTraceElement[] currentStack = Thread.getAllStackTraces().get(Thread.currentThread());

        StringBuilder bob = new StringBuilder();

        for (StackTraceElement i : currentStack)
        {
            bob.append(i.toString()).append("\n");
        }

        logger.debug("OnOpen was called with this call tree: " + bob.toString());
    }

    /**
     * @return whether or not onClose has been called
     */
    public boolean isNotClosed() {
        return notClosed;
    }
}
