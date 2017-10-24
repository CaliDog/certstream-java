package io.calidog.certstream;

import org.java_websocket.handshake.ServerHandshake;

@FunctionalInterface
public interface CertStreamOpenHandler {
    void onOpen(ServerHandshake serverHandshake);
}
