package lan_editor.networking.actions;

import lan_editor.datastore.dataClasses.Document;
import lan_editor.datastore.dataClasses.serializable.SerializableBlock;

import java.util.List;

public class RemoveBlockAction extends DocumentAction {
    int blockIndex;

    public RemoveBlockAction(String docName, int blockIndex) {
        super(docName);
        this.blockIndex = blockIndex;
    }

    @Override
    public void commit(List<SerializableBlock> document) {
        document.remove(blockIndex);
    }

    @Override
    public void commit(Document document) {
        document.getBlocks().remove(blockIndex);
    }
}
