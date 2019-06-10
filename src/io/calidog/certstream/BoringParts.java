package io.calidog.certstream;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Supplier;

/**
 * The class that takes care of all the boring parts
 * of talking over WebSockets, most notably the onClose
 * which will get called if your internet connection
 * hiccups.
 */
public class BoringParts implements
        CertStreamErrorHandler,
        CertStreamCloseHandler,
        CertStreamOpenHandler {
    private static final Logger logger = LoggerFactory.getLogger(BoringParts.class);
    private final long sleepBeforeReconnect;

    private HashSet<Integer> recoverableCloseCodes = new HashSet<>(Arrays.asList(1001, 1005, 1006, 1009));

    private final Supplier<WebSocketClient> webSocketClientSupplier;
    private WebSocketClient webSocketClient;

    private final CertStreamClientImplFactory defaultImplFactory =
            new CertStreamClientImplFactory(this,
                    this,
                    this);

    public BoringParts(CertStreamStringMessageHandler handler)
    {
        this(handler, 0);
    }

    public BoringParts(CertStreamStringMessageHandler messageHandler, long sleepBeforeReconnect) {
        this.sleepBeforeReconnect = sleepBeforeReconnect;
        this.webSocketClientSupplier = () -> defaultImplFactory.make(messageHandler);
        webSocketClient = webSocketClientSupplier.get();
    }

    //todo reconnection logic
    @Override
    public void onClose(int i, String s, boolean b) {
        logger.debug("OnClose was called wih i = " + i + ", s = "+s + ", b = b");
        System.out.println("OnClose was called wih i = " + i + ", s = "+s + ", b = b");

        if (recoverableCloseCodes.contains(i))
        {
            if (sleepBeforeReconnect >= 0)
            {
                try {
                    Thread.yield();
                    Thread.sleep(sleepBeforeReconnect);
                } catch (InterruptedException e) {
                    logger.info("Thread sleep interrupted, weird, exiting", e);
                    return;
                }
            }

            webSocketClient = webSocketClientSupplier.get();
        }
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
        return !webSocketClient.isClosed();
    }
}
