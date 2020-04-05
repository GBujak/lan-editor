package lan_editor.gui.widgets;

import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class ExpandingTextArea extends TextArea {
    private Text content;
    private double currentHeight = 50;

    public ExpandingTextArea(Text content) {
        super();
        wrapTextProperty().setValue(true);
        this.setPrefHeight(50);
        setManaged(true);

        this.content = content;
        this.textProperty().bindBidirectional(this.content.textProperty());
        this.setOnKeyPressed(keyEvent -> fitToText());
    }

    public void fitToText() {
        if (currentHeight < 50) currentHeight = 50;

        Text tx = (Text) this.lookup(".text");
        if (tx != null) {
            currentHeight = Math.max(50, tx.getBoundsInLocal().getHeight() + 10);
        }

        setPrefHeight(currentHeight);
    }
}
