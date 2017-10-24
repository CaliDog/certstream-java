package io.calidog.certstream;

public interface CertStreamStringMessageHandler {
    //todo message should be a CertStreamMessagePOJO
    void onMessage(String msg);
}
