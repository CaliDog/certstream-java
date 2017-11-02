package io.calidog.certstream;

import org.java_websocket.handshake.ServerHandshake;

/**
 * Functional interface for handling WebSocket onOpen
 */
@FunctionalInterface
public interface CertStreamOpenHandler {
    void onOpen(ServerHandshake serverHandshake);
}
