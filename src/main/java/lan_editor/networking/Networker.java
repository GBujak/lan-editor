package lan_editor.networking;

import lan_editor.gui.MainGuiController;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.channels.SocketChannel;

/**
 * Klasa zajmująca się komunikacją sieciową z serwerem i innymi klientami
 */

public class Networker implements Runnable {
    Dispatcher dispatcher;

    MainGuiController gui;
    public Networker(MainGuiController gui) {
        this.gui = gui;
        this.dispatcher = new Dispatcher();
    }

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
                dispatcher.addSocket(newClient);
                new Thread(new SocketHandler(gui, dispatcher, newClient)).start();
            } catch (IOException e) {e.printStackTrace();}
        }
    }
}