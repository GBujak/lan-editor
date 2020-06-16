package lan_editor.gui;

import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import lan_editor.datastore.Datastore;
import lan_editor.datastore.dataClasses.Block;
import lan_editor.datastore.dataClasses.BlockListCell;
import lan_editor.datastore.dataClasses.Document;
import lan_editor.datastore.dataClasses.TextBlock;
import lan_editor.gui.widgets.ExpandingTextArea;
import lan_editor.networking.Networker;
import lan_editor.networking.actions.Action;
import lan_editor.networking.actions.ChangeBlockAction;
import lan_editor.networking.actions.DocumentAction;

import java.util.function.Consumer;

/**
 * Kontroler głównego okna
 */

public class MainGuiController {
    private Networker<Action> networker;

    private Datastore datastore = new Datastore();

    private Document document = null;

    public void setDocument(Document doc) {
        document = doc;
        mainListView.setItems(document.getBlocks());
    }
    public Document getDocument() {
        return document;
    }

    @FXML
    private TreeView<?> mainTreeView;

    @FXML
    private ListView<Block> mainListView;

    @FXML
    private ToolBar mainToolBar;

    @FXML
    private Button joinButton;

    @FXML
    private Button hostButton;

    @FXML
    private TextField addressField;

    @FXML
    private TextField portField;

    @FXML
    public void initialize() {
        mainListView.setCellFactory(
                blockListView -> new BlockListCell());
        mainListView.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyEvent);

        joinButton.setOnAction(this::handleJoin);
        hostButton.setOnAction(this::handleHost);

        var doc = new Document();
        doc.getBlocks().add(new TextBlock(""));
        this.setDocument(doc);
    }

    private Consumer<Action> consumer = action -> {
        action.getDocumentAction().commit(this.getDocument());
        var node = this.getDocument().getBlocks().get(action.getBlockIndex()).getNode();
        if (node instanceof ExpandingTextArea) ((ExpandingTextArea) node).fitToText();
    };

    private void handleJoin(ActionEvent ev) {
        var url = addressField.getText();
        int port = 0;
        try {
            port = Integer.parseInt(portField.getText());
        } catch (NumberFormatException e) {
            portField.setText("");
            return;
        }

        this.networker = Networker.makeClient(
                url, port,
                consumer,
                new TypeToken<Action>(){}
        );

        var thread = new Thread(this.networker);
        thread.setDaemon(true);
        thread.start();

        portField.setDisable(true);
        addressField.setDisable(true);
    }

    private void handleHost(ActionEvent ev) {
        int port = 0;
        try {
            port = Integer.parseInt(portField.getText());
        } catch (NumberFormatException e) {
            portField.setText("");
            return;
        }

        this.networker = Networker.makeServer(
                port,
                action -> { // Jeśli jesteś serwerem, roześlij otrzymaną akcję
                    this.consumer.accept(action);
                    this.networker.send(action);
                },
                new TypeToken<Action>(){}
        );

        var thread = new Thread(this.networker);
        thread.setDaemon(true);
        thread.start();

        portField.setDisable(true);
        addressField.setDisable(true);
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

        if (selected instanceof ExpandingTextArea) {
            var textArea = (ExpandingTextArea) selected;
            networker.send(new Action(
                    new ChangeBlockAction(
                        "",
                        document.getBlocks().indexOf(textArea.getTextBlock()),
                        textArea.getText()
            )));
        }
    }
}

