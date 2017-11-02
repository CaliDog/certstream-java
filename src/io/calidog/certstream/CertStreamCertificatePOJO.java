package io.calidog.certstream;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * A POJO to house all the information derived from a
 * Certificate object as represented by CertStream
 */
public class CertStreamCertificatePOJO {

    HashMap<String, String> subject;

    HashMap<String, String> extensions;

    @SerializedName("not_before")
    double notBefore;

    @SerializedName("not_after")
    double notAfter;

    @SerializedName("as_der")
    String asDer;

}
