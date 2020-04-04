package io.calidog.certstream;

import org.java_websocket.handshake.ServerHandshake;

import java.net.URISyntaxException;

/**
 * A bundler around a functional interface. This is the little bit of magic
 * that lets you just pass in a {@link java.util.function.Consumer} and have
 * it run alongside a WebSocket
 */
public class CertStreamClientImplFactory {
    private final CertStreamOpenHandler openHandler;
    private final CertStreamCloseHandler closeHandler;
    private final CertStreamErrorHandler errorHandler;

    /**
     * A creator of CertStream client (mostly) implementations ready
     * for bundling with an onMessage function
     * @param openHandler The functional implementation of WebSocket onOpen
     * @param closeHandler The functional implementation of WebSocket onClose
     * @param errorHandler The functional implementation of WebSocket onError
     */
    public CertStreamClientImplFactory(CertStreamOpenHandler openHandler,
                                       CertStreamCloseHandler closeHandler,
                                       CertStreamErrorHandler errorHandler)
    {
        this.closeHandler = closeHandler;
        this.errorHandler= errorHandler;
        this.openHandler = openHandler;
    }

    /**
     * @param messageHandler The handler for onMessage
     * @return CertStreamClientImpl connected to the
     * default sever ("wss://certstream.calidog.io")
     */
    public CertStreamClientImpl make(CertStreamStringMessageHandler messageHandler)
    {
        return new CertStreamClientImpl(createClient(messageHandler));
    }

    /**
     *
     * @param messageHandler The handler for onMessage
     * @param serverURI String representation of the address of the alternative server
     * @return CertStreamClientImpl connected to an
     * alternative Certstream-Server
     */
    public CertStreamClientImpl make(CertStreamStringMessageHandler messageHandler, String serverURI){
        CertStreamClientImpl certStreamClient = null;
        try {
            certStreamClient = new CertStreamClientImpl(createClient(messageHandler), serverURI);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return certStreamClient;
    }

    /**
     *
     * @param messageHandler The handler for onMessage
     * @return a complete CertStreamClientImpl made out of a function
     * and the handlers this Object was initialized with
     */
    public CertStreamClient createClient(CertStreamStringMessageHandler messageHandler){
        return new CertStreamClient() {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                openHandler.onOpen(serverHandshake);
            }

            @Override
            public void onMessage(String s) {
                messageHandler.onMessage(s);
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                closeHandler.onClose(i, s, b);
            }

            @Override
            public void onError(Exception e) {
                errorHandler.onError(e);
            }
        };
    }


}
