package io.calidog.certstream;

/**
 * A functional interface for handling WebSocket errors
 */
@FunctionalInterface
public interface CertStreamErrorHandler {
    void onError(Exception e);
}
