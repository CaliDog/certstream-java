package io.calidog.certstream;

import java.security.cert.CertificateException;

/**
 * A class representing the messages sent by CertStream over WebSockets
 */
public class CertStreamMessage {

    String messageType;

    CertStreamMessageData data;

    private CertStreamMessage() {}

    //todo the field that we risk generating this exception for should be static and we can cache
    //whatever exception it throws and it it isn't null throw it here
    public static CertStreamMessage fromPOJO(CertStreamMessagePOJO pojo) throws CertificateException {
        CertStreamMessage fullMsg = new CertStreamMessage();

        fullMsg.messageType = pojo.messageType;
        fullMsg.data = CertStreamMessageData.fromPOJO(pojo.data);

        return fullMsg;
    }
}
