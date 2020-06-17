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

    private TypeToken<T> typeToken;

    public SocketHandler(
            Consumer<T> onReceive, Dispatcher<T> dispatcher,
            Socket sock, TypeToken<T> typeToken) {
        this.typeToken = typeToken;
        this.consumer = onReceive;
        this.sock = sock;
        this.dispatcher = dispatcher;
        try {
            reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        } catch (IOException e) {e.printStackTrace();}
    }

    @Override
    public void run() {
        while (!sock.isClosed()) {
            T received;

            try {
                received = new Gson().fromJson(
                    reader.readLine(), typeToken.getType());
            } catch (Exception e) {
                e.printStackTrace();
                dispatcher.remove(sock);
                return;
            }

            // uruchom na wątku interfejsu graficznego
            Platform.runLater(() -> consumer.accept(received));
        }
        dispatcher.remove(sock);
    }
}
