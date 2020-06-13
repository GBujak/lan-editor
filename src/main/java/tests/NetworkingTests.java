package tests;

import lan_editor.networking.Networker;

import java.util.ArrayList;
import java.util.TreeMap;

public class NetworkingTests {
    public static void main(String[] args) throws Exception {
        var server = Networker.<String>makeServer(
                8080,
                str -> System.out.println("serwer: " + str)
        );

        var serverThread = new Thread(server);
        serverThread.setDaemon(true);
        serverThread.start();

        var list = new ArrayList<Networker<String>>();
        for (int i = 0; i < 5; i++) {
            var klient = Networker.<String>makeClient(
                    "127.0.0.1",
                    8080,
                    str -> System.out.println("klient: " + str)
            );
            list.add(klient);
            var thread = new Thread(klient);
            thread.setDaemon(true);
            thread.start();
        }

        server.send("Hello");
        list.get(1).send("od klienta");

        Thread.sleep(1000 * 2);

//        added sock: localhost
//        added sock: localhost
//        added sock: localhost
//        added sock: localhost
//        accepted: localhost
//        added sock: localhost
//        added sock: localhost
//        sending "Hello" to localhost
//        sending "od klienta" to localhost
//        accepted: localhost
//        added sock: localhost
//        sending "Hello" to localhost
//        accepted: localhost
//        added sock: localhost
//        sending "Hello" to localhost
//        accepted: localhost
//        added sock: localhost
//        sending "Hello" to localhost
//        accepted: localhost
//        added sock: localhost
//        sending "Hello" to localhost
//        klient: Hello
//        klient: Hello
//        klient: Hello
//        klient: Hello
//        serwer: od klienta
//        klient: Hello
    }
}
