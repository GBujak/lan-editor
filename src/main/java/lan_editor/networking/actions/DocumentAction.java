package lan_editor.networking.actions;

import lan_editor.datastore.dataClasses.Document;
import lan_editor.datastore.dataClasses.serializable.SerializableBlock;

import java.io.Serializable;
import java.util.List;

public abstract class DocumentAction implements Serializable {
    private int actionId;
    private String docName;

    public DocumentAction(int actionId, String docName) {
        this.actionId = actionId;
        this.docName = docName;
    }

    public String getDocumentName() {
        return docName;
    }

    public int getActionId() {
        return actionId;
    }

    public abstract void commit(List<SerializableBlock> document);
    public abstract void commit(Document document);
}