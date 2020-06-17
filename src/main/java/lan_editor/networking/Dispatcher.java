package lan_editor.networking;

import com.google.gson.Gson;
import javafx.util.Pair;
import lan_editor.networking.actions.DocumentAction;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Dispatcher rozsyła T do socketów, które przechowuje
 * wszystkie metody są synchronizowane
 *
 * @param <T> to rozsyłany obiekt implementujący Serializable
 */
public class Dispatcher<T extends Serializable> {
    ArrayList<T> items = new ArrayList<>();

    /// Przechowuje socket oraz id ostatniego itemu, który został wysłany do tego socketa
    HashMap<Socket, Integer> sockets = new HashMap<>();

    public synchronized void addSocket(Socket sock) {
        System.out.println("added sock: " + sock.getInetAddress().getHostName());
        sockets.put(sock, 0);
        dispatch();
    }

    public synchronized void remove(Socket sock) {
        sockets.remove(sock);
    }

    public synchronized void addAndDispatch(T item) {
        items.add(item);
        dispatch();
    }

    public synchronized void dispatch() {
        for (var entry : sockets.entrySet()) {
            int lastClientAction = entry.getValue();
            Socket clientSocket  = entry.getKey();
            if (items.size() > lastClientAction) {
                for (int i = lastClientAction; i < items.size(); i++) {
                    var item = items.get(i);
                    try {
                        var writer = new PrintWriter(clientSocket.getOutputStream(), true);
                        var gson = new Gson();
                        var json = gson.toJson(item);
                        System.out.println("sending " + json + " to " 
                            + clientSocket.getInetAddress().getHostName());
                        writer.println(json);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                entry.setValue(items.size());
            }
        }
    }
}
