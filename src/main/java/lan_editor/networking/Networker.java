package lan_editor.networking;

import com.sun.tools.javac.Main;
import lan_editor.gui.MainGuiController;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Optional;

/**
 * Klasa zajmująca się komunikacją sieciową z serwerem i innymi klientami
 */

public class Networker implements Runnable {
    private Dispatcher dispatcher;
    private boolean iAmServer;

    private int port;
    private String address;

    MainGuiController gui;
    public Networker(MainGuiController gui, boolean isServer, String address, int port) {
        if (address == null && !isServer)
            throw new IllegalArgumentException("adres nie moze byc null dla klienta");
        this.address = address;
        this.port = port;
        this.iAmServer = isServer;
        this.gui = gui;
        this.dispatcher = new Dispatcher();
    }

    public static Networker makeServer(MainGuiController gui, int port) {
        return new Networker(gui, true, null, port);
    }

    public static Networker makeClient(MainGuiController gui, String address, int port) {
        return new Networker(gui, false, address, port);
    }

    ServerSocket sock;
    @Override
    public void run() {
        if (iAmServer)
             server();
        else client();
    }

    private void server() {
        try {
            sock = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                var newClient = sock.accept();
                dispatcher.addSocket(newClient);
                var thread = new Thread(new SocketHandler(gui, dispatcher, newClient));
                thread.setDaemon(true);
                thread.start();
            } catch (IOException e) {e.printStackTrace();}
        }
    }

    private void client() {
        dispatcher = new Dispatcher();
        Socket sock;
        try {
            sock = new Socket();
            sock.connect(new InetSocketAddress(address, port));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
            return; // trzeba, bo inaczej ostrzega że zmienna sock może być niezainicjalizowana
        }
        dispatcher.addSocket(sock);
        var handlerThread = new Thread(new SocketHandler(gui, dispatcher, sock));
        handlerThread.setDaemon(true);
        handlerThread.start();
    }
}