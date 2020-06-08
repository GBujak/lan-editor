package lan_editor.networking;

import com.google.gson.Gson;
import javafx.util.Pair;
import lan_editor.networking.actions.DocumentAction;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Dispatcher {
    ArrayList<DocumentAction> actions = new ArrayList<>();
    ArrayList<Pair<Socket, Integer>> sockets = new ArrayList<>();

    public void addSocket(Socket sock) {
        sockets.add(new Pair<>(sock, 0));
    }

    public void dispatch() {
        for (var client : sockets) {
            int lastClientAction = client.getValue();
            Socket clientSocket = client.getKey();
            if (actions.size() > lastClientAction) {
                for (int i = lastClientAction + 1; i < actions.size(); i++) {
                    var action = actions.get(i);
                    try {
                        var writer = new PrintWriter(clientSocket.getOutputStream(), true);
                        var gson = new Gson();
                        var json = gson.toJson(action);
                        System.out.println("wysylam " + json + " do " + clientSocket.getInetAddress().getHostName());
                        writer.println(gson.toJson(action));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
