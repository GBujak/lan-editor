package lan_editor.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.channels.SocketChannel;

/**
 * Klasa zajmująca się komunikacją sieciową z serwerem i innymi klientami
 */

public class Networker implements Runnable {
    ServerSocket sock;
    @Override
    public void run() {
        try {
            sock = new ServerSocket(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                var newClient = sock.accept();

            } catch (IOException e) {e.printStackTrace();}
        }
    }
}