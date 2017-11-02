package io.calidog.certstream;

/**
 * A functional interface for handling close
 */
public interface CertStreamCloseHandler {
    void onClose(int i, String s, boolean b);
}
