package lan_editor.networking;

import com.google.gson.reflect.TypeToken;
import com.sun.tools.javac.Main;
import lan_editor.gui.MainGuiController;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Klasa zajmująca się komunikacją sieciową z serwerem i innymi klientami
 */

public class Networker<T extends Serializable> implements Runnable {
    private Dispatcher<T> dispatcher;
    private boolean iAmServer;

    private int port;
    private String address;

    private Consumer<T> consumer;

    private TypeToken<T> typeToken;

    public Networker(
            boolean isServer, String address, int port,
            Consumer<T> onReceive, TypeToken<T> typeToken) {
        if (address == null && !isServer)
            throw new IllegalArgumentException("adres nie moze byc null dla klienta");
        this.address = address;
        this.port = port;
        this.iAmServer = isServer;
        this.consumer = onReceive;
        this.dispatcher = new Dispatcher<T>();
        this.typeToken = typeToken;
    }

    public static <T extends Serializable> Networker<T> makeServer(
        int port, Consumer<T> onReceive, TypeToken<T> responseTypeToken) {
        return new Networker<T>(true, null, port, onReceive, responseTypeToken);
    }

    public static <T extends Serializable> Networker<T> makeClient(
        String address, int port, Consumer<T> onReceive, TypeToken<T> responseTypeToken) {
        return new Networker<T>(false, address, port, onReceive, responseTypeToken);
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
                System.out.println("accepted: " + newClient.getInetAddress().getHostName());
                dispatcher.addSocket(newClient);
                var thread = new Thread(new SocketHandler<T>(
                    consumer, dispatcher, newClient, typeToken));
                thread.setDaemon(true);
                thread.start();
            } catch (IOException e) {e.printStackTrace();}
        }
    }

    private void client() {
        dispatcher = new Dispatcher<T>();
        Socket sock;
        try {
            sock = new Socket();
            sock.connect(new InetSocketAddress(address, port));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
            return;
        }
        dispatcher.addSocket(sock);
        var handlerThread = new Thread(new SocketHandler<T>(
            consumer, dispatcher, sock, typeToken));
        handlerThread.setDaemon(true);
        handlerThread.start();
    }

    public synchronized void send(T item) {
        dispatcher.addAndDispatch(item);
    }
}