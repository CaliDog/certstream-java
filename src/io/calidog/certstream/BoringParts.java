package io.calidog.certstream;

import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoringParts implements CertStreamErrorHandler, CertStreamCloseHandler, CertStreamOpenHandler {
    private static final Logger logger = LoggerFactory.getLogger(BoringParts.class);
    private boolean notClosed = true;

    @Override
    public void onClose(int i, String s, boolean b) {
        logger.debug("OnClose was called wih i = " + i + ", s = "+s + ", b = b");
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
            bob.append(i.toString());
        }

        logger.debug("OnOpen was called with this call tree: " + bob.toString());
    }

    public boolean isNotClosed() {
        return notClosed;
    }
}
