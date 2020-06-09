package lan_editor.networking;

import com.google.gson.Gson;
import javafx.application.Platform;
import lan_editor.gui.MainGuiController;
import lan_editor.networking.actions.DocumentAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.Socket;
import java.nio.channels.SocketChannel;

public class SocketHandler implements Runnable {
    private BufferedReader reader;
    private Socket sock;

    private MainGuiController gui;
    private Dispatcher dispatcher;

    public SocketHandler(MainGuiController gui, Dispatcher dispatcher, Socket sock) {
        this.gui = gui;
        this.sock = sock;
        this.dispatcher = dispatcher;
        try {
            reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        } catch (IOException e) {e.printStackTrace();}
    }

    @Override
    public void run() {
        while (!sock.isClosed()) {
            DocumentAction action;

            try {
                var json = reader.readLine();
                var gson = new Gson();
                action = gson.fromJson(json, DocumentAction.class);
            } catch (Exception e) {
                e.printStackTrace();
                dispatcher.remove(sock);
                return;
            }

            // run action on gui thread
            Platform.runLater(() -> action.commit(gui.getDocument()));
        }
    }
}
