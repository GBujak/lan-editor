package lan_editor.networking.actions;

import lan_editor.datastore.dataClasses.Document;
import lan_editor.datastore.dataClasses.serializable.SerializableBlock;

import java.util.List;

public class AddBlockAction extends DocumentAction {
    private int blockIndex;
    private SerializableBlock block;

    public AddBlockAction(
            int actionId, String docName,
            int blockIndex, SerializableBlock block) {
        super(actionId, docName);
        this.blockIndex = blockIndex;
        this.block = block;
    }

    @Override
    public void commit(List<SerializableBlock> document) {
        document.add(blockIndex, block);
    }

    @Override
    public void commit(Document document) {
        document.getBlocks()
                .add(blockIndex, block.toBlock());
    }
}
