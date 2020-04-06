package lan_editor.gui.widgets;

import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import lan_editor.gui.constants.TextTypes;

public class ExpandingTextArea extends TextArea {
    private Text content;
    private double currentHeight = getFont().getSize();

    public ExpandingTextArea(Text content) {
        super();
        wrapTextProperty().setValue(true);
        setManaged(true);
        setPrefWidth(500);

        this.content = content;
        detectFontChange();
        fitToText();

        this.textProperty().bindBidirectional(this.content.textProperty());
        this.setOnKeyPressed(keyEvent -> {
            detectFontChange();
            fitToText();
        });
    }

    public void fitToText() {
        if (currentHeight < 50) currentHeight = 50;

        Text tx = (Text) this.lookup(".text");
        if (tx != null) {
            currentHeight = Math.max(
                    getFont().getSize() + 20,
                    tx.getBoundsInLocal().getHeight() + 20);
        }

        setPrefHeight(currentHeight);
    }

    private void detectFontChange() {
        if (content.getText().startsWith("# "))
            this.setFont(TextTypes.header1);
        else this.setFont(TextTypes.defaultFont);
    }
}
