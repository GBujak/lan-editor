package lan_editor.datastore.dataClasses;

import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

/**
 * Blok tekstu w różnych formatach
 *  - tytuł
 *  - tekst monospace
 *  - zwykły paragraf
 * itd.
 */

public class TextBlock extends Block {
    Text content = new Text();
    public TextBlock(String str) {
        content.setText(str);
    }

    public void fitToText(TextArea textArea) {
        Text tex = (Text) textArea.lookup(".text");
        if (tex == null)  {
            textArea.setPrefHeight(50);
            return;
        }
        double height = tex.getBoundsInLocal().getHeight() + 20;
        if (height < 50) height = 50;
        textArea.setPrefHeight(height);
    }

    @Override
    public Node getNode() {
        var textArea = new TextArea();
        textArea.textProperty().bindBidirectional(content.textProperty());

        textArea.wrapTextProperty().setValue(true);
        fitToText(textArea);
        textArea.setManaged(true);

        textArea.setOnKeyTyped(ev -> fitToText(textArea));

        return textArea;
    }
}
