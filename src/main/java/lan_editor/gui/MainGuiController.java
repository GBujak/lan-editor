package lan_editor.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeView;

import lan_editor.datastore.Datastore;
import lan_editor.datastore.dataClasses.Block;
import lan_editor.datastore.dataClasses.BlockListCell;
import lan_editor.datastore.dataClasses.Document;

/**
 * Kontroler głównego okna
 */

public class MainGuiController {
    private Datastore datastore = null;
    void injectDatastore(Datastore ds) {
        datastore = ds;
    }

    private Document document = null;
    public void setDocument(Document doc) {
        document = doc;
        mainListView.setItems(document.getBlocks());
    }

    @FXML
    private TreeView<?> mainTreeView;

    @FXML
    private ListView<Block> mainListView;

    @FXML
    private ToolBar mainToolBar;

    @FXML
    public void initialize() {
        mainListView.setCellFactory(
                blockListView -> new BlockListCell());
        if (document == null) document = new Document();
        mainListView.setItems(document.getBlocks());
    }
}
