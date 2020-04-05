package lan_editor.datastore.dataClasses;

import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import lan_editor.gui.widgets.ExpandingTextArea;

public class BlockListCell extends ListCell<Block> {
    @Override
    protected void updateItem(Block item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText(null);
            var node = item.getNode();
            setGraphic(node);
        }
    }
}
