package lan_editor.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeView;

import lan_editor.datastore.Datastore;
import lan_editor.datastore.dataClasses.BlockInterface;

/**
 * Kontroler głównego okna
 */

public class MainGuiController {
    private Datastore datastore = null;
    void injectDatastore(Datastore ds) {
        datastore = ds;
    }

    @FXML
    private TreeView<?> mainTreeView;

    @FXML
    private ListView<BlockInterface> mainListView;

    @FXML
    private ToolBar mainToolBar;

    @FXML
    public void initialize() {
    }
}
