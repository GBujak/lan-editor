package lan_editor.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeView;

public class MainGuiController {

    @FXML
    private TreeView<?> mainTreeView;

    @FXML
    private ListView<Integer> mainListView;

    @FXML
    private ToolBar mainToolBar;

    @FXML
    public void initialize() {
        mainListView.getItems().addAll(150);
    }
}
