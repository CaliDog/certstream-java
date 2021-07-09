package io.calidog.certstream;

import com.google.gson.annotations.SerializedName;

public class CertStreamMessageDataPOJO {
    @SerializedName("update_type")
    String updateType;

    @SerializedName("cert_link")
    String certLink;

    @SerializedName("leaf_cert")
    CertStreamCertificatePOJO leafCert;

    @SerializedName("cert_index")
    long certIndex;

    double seen;

    CertStreamCertificateSource source;
}
