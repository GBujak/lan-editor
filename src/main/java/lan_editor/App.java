package lan_editor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lan_editor.datastore.dataClasses.Document;
import lan_editor.datastore.dataClasses.TextBlock;
import lan_editor.gui.MainGuiController;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("mainLayout"));
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        MainGuiController ctrl = fxmlLoader.getController();
        var doc = new Document();
        for (int i = 0; i < 20; i++)
            doc.add(new TextBlock("Hello world"));
        System.out.println(ctrl);
        ctrl.setDocument(doc);
        return root;
    }

    public static void main(String[] args) {
        launch(args);
    }

}