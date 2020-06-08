package lan_editor.networking.actions;

import lan_editor.datastore.dataClasses.Document;
import lan_editor.datastore.dataClasses.serializable.SerializableBlock;

import java.io.Serializable;
import java.util.List;

public abstract class DocumentAction implements Serializable {
    private String docName;

    public DocumentAction(String docName) {
        this.docName = docName;
    }

    public String getDocumentName() {
        return docName;
    }

    public abstract void commit(List<SerializableBlock> document);
    public abstract void commit(Document document);
}