package io.calidog.certstream;

import java.security.cert.Certificate;
import java.security.cert.CertificateException;

/**
 * The meaty data part of a CertStream Message.
 * Mostly just a POJO for now.
 */
public class CertStreamMessageData {

    String updateType;

    String certLink;

    CertStreamCertificate leafCert;

    long certIndex;

    double seen;

    CertStreamCertificateSource source;

    private CertStreamMessageData() {}

    /**
     *
     * @param pojo
     * @return
     * @throws CertificateException
     */
    public static CertStreamMessageData fromPOJO(CertStreamMessageDataPOJO pojo) throws CertificateException {
        CertStreamMessageData fullData = new CertStreamMessageData();

        fullData.updateType = pojo.updateType;

        fullData.leafCert = CertStreamCertificate.fromPOJO(pojo.leafCert);

        fullData.certLink = pojo.certLink;

        fullData.source = pojo.source;

        fullData.certIndex = pojo.certIndex;
        fullData.seen = pojo.seen;

        return fullData;
    }
}
