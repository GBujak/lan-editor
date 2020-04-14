package lan_editor.datastore.dataClasses.serializable;

import lan_editor.datastore.dataClasses.Block;
import lan_editor.datastore.dataClasses.TextBlock;

import java.io.Serializable;

public class SerializableTextBlock extends SerializableBlock
        implements Serializable {
    private String content;
    public SerializableTextBlock(String content) {
        this.content = content;
    }

    @Override
    public Block toBlock() {
        return new TextBlock(content);
    }

    @Override
    public void setContent(String newContent) {
        content = newContent;
    }
}
