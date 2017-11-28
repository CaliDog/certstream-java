package io.calidog.certstream;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class CertStreamClientImpl extends WebSocketClient{
    private static final String certStreamUriString = "wss://certstream.calidog.io";

    private static URI tempUri = null;
    static {
        try
        {
            tempUri = new URI(certStreamUriString);
        } catch (URISyntaxException ignored) {}
    }

    private static final URI certStreamUri = tempUri;

    private final CertStreamClient client;

    public CertStreamClientImpl(CertStreamClient client) {
        super(certStreamUri);
        this.client = client;
        this.connect();
    }

    public void onOpen(ServerHandshake serverHandshake) {
        client.onOpen(serverHandshake);
    }

    public void onMessage(String s) {
        client.onMessage(s);
    }

    public void onClose(int i, String s, boolean b) {
        client.onClose(i, s, b);
    }

    public void onError(Exception e) {
        client.onError(e);
    }
}
