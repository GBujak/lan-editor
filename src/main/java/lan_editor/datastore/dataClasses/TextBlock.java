package lan_editor.datastore.dataClasses;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import lan_editor.gui.widgets.ExpandingTextArea;

/**
 * Blok tekstu w różnych formatach
 *  - tytuł
 *  - tekst monospace
 *  - zwykły paragraf
 * itd.
 */

public class TextBlock extends Block {
    private Text content = new Text();
    private ExpandingTextArea textArea = new ExpandingTextArea(content);

    public TextBlock(String str) {
        content.setText(str);
    }

    @Override
    public Node getNode() {
        return textArea;
    }
}
