package tests;

import com.google.gson.reflect.TypeToken;
import lan_editor.networking.Networker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

class testClass implements Serializable {
    public int x;
    public String str;
}

public class NetworkingTests {
    public static void main(String[] args) throws Exception {
        var server = Networker.<String>makeServer(
                8080,
                str -> System.out.println("serwer: " + str),
                new TypeToken<String>(){}
        );

        var serverThread = new Thread(server);
        serverThread.setDaemon(true);
        serverThread.start();

        var list = new ArrayList<Networker<String>>();
        for (int i = 0; i < 5; i++) {
            var index = i + 1;
            var klient = Networker.<String>makeClient(
                    "127.0.0.1",
                    8080,
                    str -> System.out.println("klient " + index +": " + str),
                    new TypeToken<String>(){}
            );
            list.add(klient);
            var thread = new Thread(klient);
            thread.setDaemon(true);
            thread.start();
        }

        server.send("Hello");
        server.send("Hello2");
        server.send("Hello3");
        list.get(1).send("od klienta");

        Thread.sleep(1000 * 2);

        var newClient = Networker.makeClient(
                "localhost", 8080,
                str -> System.out.println("nowy klient: " + str),
                new TypeToken<String>(){}
        );
        var newThread = new Thread(newClient);
        newThread.setDaemon(true);
        newThread.start();

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
//        sending "Hello2" to localhost
//        sending "Hello3" to localhost
//        accepted: localhost
//        added sock: localhost
//        sending "Hello" to localhost
//        sending "Hello2" to localhost
//        sending "Hello3" to localhost
//        accepted: localhost
//        added sock: localhost
//        sending "Hello" to localhost
//        sending "Hello2" to localhost
//        sending "Hello3" to localhost
//        accepted: localhost
//        added sock: localhost
//        sending "Hello" to localhost
//        sending "Hello2" to localhost
//        sending "Hello3" to localhost
//        accepted: localhost
//        added sock: localhost
//        sending "Hello" to localhost
//        sending "Hello2" to localhost
//        sending "Hello3" to localhost
//        serwer: od klienta
//        klient 2: Hello
//        klient 3: Hello
//        klient 2: Hello2
//        klient 4: Hello
//        klient 1: Hello
//        klient 4: Hello2
//        klient 4: Hello3
//        klient 5: Hello
//        klient 1: Hello2
//        klient 5: Hello2
//        klient 5: Hello3
//        klient 2: Hello3
//        klient 3: Hello2
//        klient 1: Hello3
//        klient 3: Hello3
//        added sock: localhost
//        accepted: localhost
//        added sock: localhost
//        sending "Hello" to localhost
//        sending "Hello2" to localhost
//        sending "Hello3" to localhost
//        nowy klient: Hello
//        nowy klient: Hello2
//        nowy klient: Hello3

        var classServer = Networker.<testClass>makeServer(8888, x -> {}, new TypeToken<testClass>(){});
        var classServerThread = new Thread(classServer);
        classServerThread.setDaemon(true);
        classServerThread.start();

        var classClient = Networker.<testClass>makeClient("localhost", 8888,
                x -> System.out.println(x.x + " " + x.str),
                new TypeToken<testClass>(){});
        var classClientThread = new Thread(classClient);
        classClientThread.setDaemon(true);
        classClientThread.start();

        var test = new testClass();
        test.x = 100;
        test.str = "test string";
        classServer.send(test);

        Thread.sleep(1 * 1000);
    }
}
