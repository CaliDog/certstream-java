package io.calidog.certstream;

@FunctionalInterface
public interface CertStreamMessageHandler {
    void onMessage(CertStreamMessage message);
}
