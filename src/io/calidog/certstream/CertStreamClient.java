package io.calidog.certstream;

import org.java_websocket.handshake.ServerHandshake;

/**
 * An interface for websocket handlers
 */
public interface CertStreamClient {
    void onOpen(ServerHandshake serverHandshake);

    void onMessage(String s);

    void onClose(int i, String s, boolean b);

    void onError(Exception e);
}
