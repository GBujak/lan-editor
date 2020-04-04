package lan_editor.datastore.dataClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Klasa reprezentuje jeden dokument składający się z wielu bloków
 */

public class Document {
    private ObservableList<BlockInterface> blocks
            = FXCollections.observableArrayList();

    public ObservableList<BlockInterface> getBlocks() {
        return blocks;
    }
}
