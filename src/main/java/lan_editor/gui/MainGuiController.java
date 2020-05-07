package lan_editor.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeView;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lan_editor.datastore.Datastore;
import lan_editor.datastore.dataClasses.Block;
import lan_editor.datastore.dataClasses.BlockListCell;
import lan_editor.datastore.dataClasses.Document;
import lan_editor.datastore.dataClasses.TextBlock;
import lan_editor.gui.widgets.ExpandingTextArea;

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
        mainListView.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyEvent);

        var doc = new Document();
        doc.getBlocks().add(new TextBlock(""));
        this.setDocument(doc);
    }

    private void handleKeyEvent(KeyEvent ev) {
        // Enter tworzy nowe pole
        var selected = mainListView.getScene().getFocusOwner();
        if (ev.getCode() == KeyCode.ENTER) {
            if (selected instanceof ExpandingTextArea && !ev.isShiftDown()) {
                var selTextArea = (ExpandingTextArea) selected;
                var caret = selTextArea.getCaretPosition();
                var newBlock = new TextBlock(selTextArea.getText().substring(caret));
                selTextArea.setText(selTextArea.getText(0, caret));
                document.getBlocks().add(
                        document.getBlocks().indexOf(selTextArea.getTextBlock()) + 1,
                        newBlock
                );
                newBlock.getNode().requestFocus();
                ev.consume();
            }
        }

        // Backspace usuwa puste pole
        if (ev.getCode() == KeyCode.BACK_SPACE) {
            if (selected instanceof ExpandingTextArea && !ev.isShiftDown()) {
                var selTextArea = (ExpandingTextArea) selected;
                var caretPos = selTextArea.getCaretPosition();
                if (caretPos != 0) return;

                var remainingText = selTextArea.getText();
                var index = document.getBlocks().indexOf(selTextArea.getTextBlock());
                if (index == 0) return;

                var prevBlock = document.getBlocks().get(index - 1);
                if (prevBlock instanceof TextBlock) {
                    var prevText = (TextBlock) prevBlock;
                    var newCaret = prevText.getContent().getText().length();
                    prevText.getContent().setText(
                            prevText.getContent().getText().concat(remainingText)
                    );
                    document.getBlocks().remove(index);
                    prevText.getNode().positionCaret(newCaret);
                    prevText.getNode().requestFocus();
                }
            }
        }
    }
}

