package example;

import com.google.gson.Gson;
import io.calidog.certstream.CertStream;

/**
 * Demo Client that prints out a message of a certstream-server with a given URI
 * The server address of the CaliDog Server is entered here for demo purposes
 */
public class ExampleClientAlternativeServer {

    public static void main (String[] args){

        CertStream.onMessageAlternativeServer(msg -> System.out.println(new Gson().toJson(msg)), "wss://certstream.calidog.io");

    }

}
