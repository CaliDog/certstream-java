package example;

import com.google.gson.Gson;
import io.calidog.certstream.CertStream;

/**
 * Prints out Messages
 */
public class ExampleClient {

    /**
     * Main method
     * @param args unused
     */
    public static void main(String[] args)
    {
        //string version of the message
        CertStream.onMessageString(System.out::println);


        CertStream.onMessage(msg -> System.out.println(new Gson().toJson(msg)));
    }
}
