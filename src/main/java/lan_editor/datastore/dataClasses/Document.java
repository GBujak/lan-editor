package lan_editor.datastore.dataClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Klasa reprezentuje jeden dokument składający się z wielu bloków
 */

public class Document {
    private ObservableList<Block> blocks
            = FXCollections.observableArrayList();

    public ObservableList<Block> getBlocks() {
        return blocks;
    }

    public void add(Block bl) {
        blocks.add(bl);
    }
}
