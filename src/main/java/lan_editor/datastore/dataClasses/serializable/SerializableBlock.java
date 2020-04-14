package lan_editor.datastore.dataClasses.serializable;

import lan_editor.datastore.dataClasses.Block;

import java.io.Serializable;

public abstract class SerializableBlock
        implements Serializable {
    public abstract Block toBlock();
}
