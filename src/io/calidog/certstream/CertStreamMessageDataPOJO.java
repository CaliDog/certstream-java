package io.calidog.certstream;

import com.google.gson.annotations.SerializedName;

public class CertStreamMessageDataPOJO {
    @SerializedName("update_type")
    String updateType;

    @SerializedName("leaf_cert")
    CertStreamCertificatePOJO leafCert;

    CertStreamCertificatePOJO[] chain;

    @SerializedName("cert_index")
    long certIndex;

    double seen;

    CertStreamCertificateSource source;
}
