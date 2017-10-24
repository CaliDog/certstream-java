package example;

import io.calidog.certstream.CertStream;

public class ExampleClient {
    public static void main(String[] args)
    {
//        CertStream.openStream().forEach(System.out::println);

        //string version of the message
        CertStream.onMessage(System.out::println);

        //todo pojo version of the message
    }
}
