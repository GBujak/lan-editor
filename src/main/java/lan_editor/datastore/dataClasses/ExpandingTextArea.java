package lan_editor.datastore.dataClasses;

import javafx.scene.control.TextArea;

public class ExpandingTextArea extends TextArea {
    public ExpandingTextArea() {
        super();
    }
    int getLineCount() {
        return getParagraphs().size();
    }
}
