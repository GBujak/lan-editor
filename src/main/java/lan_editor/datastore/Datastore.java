package lan_editor.datastore;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import lan_editor.datastore.dataClasses.Document;
import lan_editor.datastore.dataClasses.serializable.SerializableBlock;

import java.io.*;
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

    public boolean saveToStream(OutputStream stream) {
        var writer = new OutputStreamWriter(stream);
        var gson = new Gson();
        try {
            writer.write(gson.toJson(documents));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean loadFromStream(InputStream stream) {
        var reader = new BufferedReader(new InputStreamReader(stream));
        var gson = new Gson();

        try {
            documents = gson.fromJson(reader.readLine(), new TypeToken<
                    Hashtable<String, List<SerializableBlock>>>(){}.getType());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
