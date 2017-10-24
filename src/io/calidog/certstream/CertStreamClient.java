package io.calidog.certstream;

import org.java_websocket.handshake.ServerHandshake;

public interface CertStreamClient {
    void onOpen(ServerHandshake serverHandshake);

    void onMessage(String s);

    void onClose(int i, String s, boolean b);

    void onError(Exception e);
}
