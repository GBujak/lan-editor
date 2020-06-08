package lan_editor.networking.actions;

import lan_editor.datastore.dataClasses.Document;
import lan_editor.datastore.dataClasses.serializable.SerializableBlock;

import java.util.List;

public class ChangeBlockAction extends DocumentAction {
    int blockIndex;
    String blockContent;

    public ChangeBlockAction(
            int actionId, String docName, int blockIndex, String blockContent) {
        super(docName);
        this.blockIndex = blockIndex;
        this.blockContent = blockContent;
    }

    @Override
    public void commit(List<SerializableBlock> document) {
        document.get(blockIndex).setContent(blockContent);
    }

    @Override
    public void commit(Document document) {
        document.getBlocks().get(blockIndex)
                .setContent(blockContent);
    }
}
