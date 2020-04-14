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
    public Text getContent() {
        return content;
    }

    public void setContent(String newContent) {
        content.setText(newContent);
    }

    private ExpandingTextArea textArea = new ExpandingTextArea(this);

    public TextBlock(String str) {
        content.setText(str);
    }

    @Override
    public ExpandingTextArea getNode() {
        return textArea;
    }
}
