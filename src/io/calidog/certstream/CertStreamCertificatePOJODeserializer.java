package io.calidog.certstream;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class CertStreamCertificatePOJODeserializer implements JsonDeserializer<CertStreamCertificatePOJO> {

    @Override
    public CertStreamCertificatePOJO deserialize(
            JsonElement jsonElement,
            Type type,
            JsonDeserializationContext jsonDeserializationContext
    ) throws JsonParseException {

        JsonObject jsonObj = jsonElement.getAsJsonObject();
        JsonObject jsonExtensions = new JsonObject();

        // remove extensions from json object
        if (jsonObj.has("extensions"))
        {
            jsonExtensions = jsonObj.remove("extensions").getAsJsonObject();
        }

        // parse entry normally
        CertStreamCertificatePOJO retVal = new Gson().fromJson(jsonElement, CertStreamCertificatePOJO.class);

        // parse extensions externally
        retVal.extensions = deserializeExtension(jsonExtensions);

        return retVal;
    }

    private HashMap<String, String[]> deserializeExtension(JsonObject extensions){
        final HashMap<String, String[]> finalMap = new HashMap<>(extensions.size());

        extensions.entrySet().forEach((Map.Entry<String, JsonElement> entry) -> {
            String key = entry.getKey();
            JsonElement value = entry.getValue();
            String[] extensionValues;

            try {
                extensionValues = new String[] { value.getAsString() };
            } catch (IllegalStateException | UnsupportedOperationException e) {
                JsonArray extensionJsonArray = value.getAsJsonArray();
                extensionValues = new String[extensionJsonArray.size()];

                for (int i = 0; i < extensionJsonArray.size(); i++) {
                    extensionValues[i] = extensionJsonArray.get(i).getAsString();
                }
            }

            finalMap.put(key, extensionValues);
        });

        return finalMap;
    }
}
