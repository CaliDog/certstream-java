package io.calidog.certstream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.cert.CertificateException;
import java.util.function.Consumer;

/**
 * The main class for handling {@link CertStreamMessage}s.
 */
public class CertStream{

    private static final Logger logger = LoggerFactory.getLogger(CertStream.class);

    /**
     * @param handler A {@link Consumer<String>} that we'll
     *                run in a Thread that stays alive as long
     *                as the WebSocket stays open.
     */
    public static void onMessageString(Consumer<String> handler)
    {
        new Thread(() ->
        {
            BoringParts theBoringParts = new BoringParts(handler::accept);

            runThread(theBoringParts);

        }).start();
    }

    /**
     * @param handler A {@link Consumer<String>} that we'll
     *                run in a Thread that stays alive as long
     *                as the WebSocket stays open
     * @param serverURI Address of the server to which a  WebSocket
     *                  connection is established
     */
    public static void onMessageStringAlternativeServer(Consumer<String> handler, String serverURI){
        new Thread(() -> {
            BoringParts theBoringParts = new BoringParts(handler::accept, 0 , serverURI);

            runThread(theBoringParts);

        }).start();
    }


    /**
     * @param handler A {@link Consumer<CertStreamMessage>} that we'll
     *                run in a Thread that stays alive as long
     *                as the WebSocket stays open.
     */
    public static void onMessage(CertStreamMessageHandler handler)
    {
        onMessageString(string ->
        {
            CertStreamMessagePOJO msg;
            try
            {
                msg = certStreamGson.fromJson(string, CertStreamMessagePOJO.class);

                if (msg.messageType.equalsIgnoreCase("heartbeat"))
                {
                    return;
                }
            } catch (JsonSyntaxException e)
            {
                System.out.println(e.getMessage());
                logger.warn("onMessage had an exception parsing some json", e);
                return;
            }

            CertStreamMessage fullMsg;

            try
            {
                fullMsg = CertStreamMessage.fromPOJO(msg);
            } catch (CertificateException e) {
                logger.warn("Encountered a CertificateException", e);
                return;
            }

            handler.onMessage(fullMsg);
        });
    }

    private static Gson certStreamGson =
            new GsonBuilder()
                    .registerTypeAdapter
                    (
                        CertStreamCertificatePOJO.class,
                        new CertStreamCertificatePOJODeserializer()
                    )
                    .create();

    /**
     * @param handler A {@link Consumer<CertStreamMessage>} that we'll
     *                run in a Thread that stays alive as long
     *                as the WebSocket stays open
     * @param serverURI Address of the server to which a  WebSocket
     *                  connection is established
     */
    public static void onMessageAlternativeServer (CertStreamMessageHandler handler, String serverURI){
        onMessageStringAlternativeServer(string -> {
            CertStreamMessagePOJO msg;

            try {
                msg = certStreamGson.fromJson(string, CertStreamMessagePOJO.class);

                if (msg.messageType.equalsIgnoreCase("heartbeat")) {
                    return;
                }
            } catch (JsonSyntaxException e) {
                System.out.println(e.getMessage());
                logger.warn("onMessageAlternativeServer had an exception parsing some json", e);
                return;
            }

            CertStreamMessage fullMsg;

            try {
                fullMsg = CertStreamMessage.fromPOJO(msg);
            } catch (CertificateException e){
                logger.warn("Encountered a CertificateException", e);
                return;
            }

            handler.onMessage(fullMsg);
        }, serverURI);
    }

    /**
     * Runs thread until WebSocket is closed
     * @param theBoringParts Websocket connection that is checked
     *                       to see if it has been closed
     */
    public static void runThread(BoringParts theBoringParts) {
        while (theBoringParts.isNotClosed())
        {
            Thread.yield();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}

            if (Thread.interrupted())
            {
                break;
            }
        }
    }
}
