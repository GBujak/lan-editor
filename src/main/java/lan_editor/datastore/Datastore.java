package lan_editor.datastore;

import com.google.gson.Gson;
import lan_editor.datastore.dataClasses.Document;
import lan_editor.datastore.dataClasses.serializable.SerializableBlock;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

/**
 * Klasa zajmuje się przechowywaniem dokumentów i zapisywaniem ich na dysku
 */

public class Datastore {
    Hashtable<String, List<SerializableBlock>> documents;

    // Dostań dokument po imieniu
    public Optional<Document> loadDocument(String name) {
        if (!documents.contains(name))
            return Optional.empty();
        var list = documents.get(name);
        var doc = new Document();
        for (var i : list)
            doc.getBlocks().add(i.toBlock());
        return Optional.of(doc);
    }

    public void saveToStream(OutputStream stream) {
    }

    public void loadFromStream(InputStream stream) {
    }
}
