package io.calidog.certstream;

@FunctionalInterface
public interface CertStreamErrorHandler {
    void onError(Exception e);
}
