package io.calidog.certstream;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.cert.CertificateException;
import java.util.function.Consumer;

public class CertStream{

    private static final Logger logger = LoggerFactory.getLogger(CertStream.class);

    private static final long defaultRateLimit = 1000;

    private static BoringParts theBoringParts = new BoringParts();

    private static CertStreamClientImplFactory defaultImplFactory =
            new CertStreamClientImplFactory(theBoringParts,
                    theBoringParts,
                    theBoringParts);

    private static final Gson gson= new Gson();

//    public static Stream<CertStreamMessagePOJO> openStream()
//    {
//        return openStream(defaultRateLimit);
//    }
//
//    public static Stream<CertStreamMessagePOJO> openStream(long rateLimit)
//    {
//
//
//    }


    //todo another function that accepts an acceptor that accepts the POJOs parsed out of the json
    public static void onMessageString(Consumer<String> handler)
    {
        new Thread(() ->
        {
            defaultImplFactory.make(handler::accept);

            while (theBoringParts.isNotClosed())
            {
                Thread.yield();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }).start();
    }

    public static void onMessage(CertStreamMessageHandler handler)
    {
        onMessageString(string ->
        {
            CertStreamMessagePOJO msg = null;
            try
            {
                msg = gson.fromJson(string, CertStreamMessagePOJO.class);

                if (msg.messageType.equalsIgnoreCase("heartbeat"))
                {
                    System.out.println("heartbeat message: " + string);
                    return;
                }



                System.out.println(msg.data.leafCert);
            }catch (JsonSyntaxException e)
            {
                System.out.println(e.getMessage());
                logger.warn("onMessage had an exception parsing some json", e);
            }

            CertStreamMessage fullMsg = null;

            try
            {
                fullMsg = CertStreamMessage.fromPOJO(msg);
            } catch (CertificateException e) {
                e.printStackTrace();
                return;
            }

            handler.onMessage(fullMsg);
        });
    }
}
