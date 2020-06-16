package lan_editor.networking.actions;

import lan_editor.datastore.dataClasses.Document;
import lan_editor.datastore.dataClasses.TextBlock;
import lan_editor.datastore.dataClasses.serializable.SerializableBlock;
import lan_editor.datastore.dataClasses.serializable.SerializableTextBlock;

import java.util.List;

public class AddBlockAction extends DocumentAction {
    int blockIndex;
    String blockContent;

    public AddBlockAction(String docName,
            int blockIndex, String blockContent) {
        super(docName);
        this.blockIndex = blockIndex;
        this.blockContent = blockContent;
    }

    @Override
    public void commit(List<SerializableBlock> document) {
        document.add(blockIndex, new SerializableTextBlock(blockContent));
    }

    @Override
    public void commit(Document document) {
        document.getBlocks()
                .add(blockIndex, new TextBlock(blockContent));
    }
}
