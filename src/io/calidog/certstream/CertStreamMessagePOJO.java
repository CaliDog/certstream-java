package io.calidog.certstream;

import com.google.gson.annotations.SerializedName;

public class CertStreamMessagePOJO {
    @SerializedName("message_type")
    String messageType;

    CertStreamMessageDataPOJO data;
}
