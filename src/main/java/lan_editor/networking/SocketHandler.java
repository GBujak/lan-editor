package lan_editor.networking;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import lan_editor.gui.MainGuiController;
import lan_editor.networking.actions.DocumentAction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.function.Consumer;

public class SocketHandler<T extends Serializable> implements Runnable {
    private BufferedReader reader;
    private Socket sock;

    private Consumer<T> consumer;
    private Dispatcher<T> dispatcher;

    private Type typetoken;

    public SocketHandler(Consumer<T> onReceive, Dispatcher<T> dispatcher, Socket sock) {
        typetoken = new TypeToken<T>(){}.getType();
        this.consumer = onReceive;
        this.sock = sock;
        this.dispatcher = dispatcher;
        try {
            reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        } catch (IOException e) {e.printStackTrace();}
    }

    static private <T> T parseJson(String json) {
        var gson = new Gson();
        var type = new TypeToken<T>(){}.getType();
        return gson.fromJson(json, type);
    }

    @Override
    public void run() {
        while (!sock.isClosed()) {
            T received;

            try {
                received = SocketHandler.<T>parseJson(reader.readLine());
            } catch (Exception e) {
                e.printStackTrace();
                dispatcher.remove(sock);
                return;
            }

            consumer.accept(received);
        }
        dispatcher.remove(sock);
    }
}
