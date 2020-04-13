package lan_editor.gui.widgets;

import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import lan_editor.datastore.dataClasses.TextBlock;
import lan_editor.gui.constants.TextTypes;

public class ExpandingTextArea extends TextArea {
    private TextBlock textBlock;
    private Text content;
    private double currentHeight = getFont().getSize() + 20;

    public TextBlock getTextBlock() {
        return textBlock;
    }

    public ExpandingTextArea(TextBlock textBlock) {
        super();
        wrapTextProperty().setValue(true);
        setManaged(true);
        setPrefWidth(500);

        this.textBlock = textBlock;
        this.content = this.textBlock.getContent();
        this.textProperty().bindBidirectional(this.content.textProperty());

        detectFontChange();
        fitToText();

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
        else if (content.getText().startsWith("## "))
            this.setFont(TextTypes.header2);
        else if (content.getText().startsWith("### "))
            this.setFont(TextTypes.header3);
        else if (this.getFont() != TextTypes.defaultFont)
            this.setFont(TextTypes.defaultFont);
        else if (this.getText().startsWith("```"))
            this.setFont(TextTypes.monoFont);
    }
}
