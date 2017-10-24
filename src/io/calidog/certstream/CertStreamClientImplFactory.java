package io.calidog.certstream;

import org.java_websocket.handshake.ServerHandshake;

public class CertStreamClientImplFactory {
    private final CertStreamOpenHandler openHandler;
    private final CertStreamCloseHandler closeHandler;
    private final CertStreamErrorHandler errorHandler;
    public CertStreamClientImplFactory(CertStreamOpenHandler openHandler,
                                       CertStreamCloseHandler closeHandler,
                                       CertStreamErrorHandler errorHandler)
    {
        this.closeHandler = closeHandler;
        this.errorHandler= errorHandler;
        this.openHandler = openHandler;
    }

    public CertStreamClientImpl make(CertStreamStringMessageHandler messageHandler)
    {
        return new CertStreamClientImpl(new CertStreamClient() {
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
        });
    }


}
