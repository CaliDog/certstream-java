package io.calidog.certstream;

import java.security.cert.Certificate;
import java.security.cert.CertificateException;

/**
 * The meaty data part of a CertStream Message.
 * Mostly just a POJO for now.
 */
public class CertStreamMessageData {

    String updateType;

    Certificate leafCert;

    Certificate[] chain;

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

        fullData.chain = new CertStreamCertificate[pojo.chain.length];

        for (int i = 0; i < fullData.chain.length; i++)
        {
            fullData.chain[i] = CertStreamCertificate.fromPOJO(pojo.chain[i]);
        }

        fullData.source = pojo.source;

        fullData.certIndex = pojo.certIndex;
        fullData.seen = pojo.seen;

        return fullData;
    }
}
