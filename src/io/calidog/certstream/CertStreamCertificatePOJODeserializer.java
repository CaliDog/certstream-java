package io.calidog.certstream;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;

public class CertStreamCertificatePOJODeserializer implements JsonDeserializer<CertStreamCertificatePOJO> {

    @Override
    public CertStreamCertificatePOJO deserialize(
            JsonElement jsonElement,
            Type type,
            JsonDeserializationContext jsonDeserializationContext
    ) throws JsonParseException {
        JsonObject jsonObj = jsonElement.getAsJsonObject();

        JsonObject jsonExtensions;

        HashMap<String, String[]> extensionMap = null;

        if (jsonObj.has("extensions"))
        {
            jsonExtensions = jsonObj.remove("extensions").getAsJsonObject();

            final HashMap<String, String[]> finalExtensionMap = new HashMap<>(jsonExtensions.size());

            jsonExtensions
                    .entrySet()
                    .forEach
                    (
                            (java.util.Map.Entry<String, JsonElement> entry) ->
                            {
                                String key = entry.getKey();
                                JsonElement value = entry.getValue();
                                String[] extensionValueList;
                                try {
                                    extensionValueList = new String[] { value.getAsString() };
                                } catch (IllegalStateException | UnsupportedOperationException e) {
                                    JsonArray extensionJsonArray = value.getAsJsonArray();
                                    extensionValueList = new String[extensionJsonArray.size()];

                                    for (int i = 0; i < extensionJsonArray.size(); i++) {
                                        extensionValueList[i] = extensionJsonArray.get(i).getAsString();
                                    }
                                }

                                finalExtensionMap.put(key, extensionValueList);
                            }
                    );

            extensionMap = finalExtensionMap;
        }

        CertStreamCertificatePOJO retVal =
                jsonDeserializationContext.deserialize(jsonElement, type);

        retVal.extensions = extensionMap;

        return retVal;
    }
}
