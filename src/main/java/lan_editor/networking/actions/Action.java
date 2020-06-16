package lan_editor.networking.actions;

import lan_editor.datastore.dataClasses.serializable.SerializableBlock;
import lan_editor.datastore.dataClasses.serializable.SerializableTextBlock;

import java.io.Serializable;

/**
 *  Klasa do przesyłania akcji
 *
 *  Potrzebna, bo nie można przesyłać klasy abstrakcyjnej
 */

public class Action implements Serializable {
    public enum ActionType {
        DocumentAction,
        GlobalAction
    }

    public enum DocumentActionType {
        AddBlockAction,
        ChangeBlockAction,
        RemoveBlockAction
    }

    private ActionType actionType = null;
    private DocumentActionType documentActionType = null;
    private String content = null;
    private int blockIndex = -1;

    public Action(ChangeBlockAction action) {
        actionType = ActionType.DocumentAction;
        documentActionType = DocumentActionType.ChangeBlockAction;
        content = action.blockContent;
        blockIndex = action.blockIndex;
    }

    public Action(AddBlockAction action) {
        actionType = ActionType.DocumentAction;
        documentActionType = DocumentActionType.AddBlockAction;
        content = action.blockContent;
        blockIndex = action.blockIndex;
    }

    public Action(RemoveBlockAction action) {
        actionType = ActionType.DocumentAction;
        documentActionType = DocumentActionType.RemoveBlockAction;
        blockIndex = action.blockIndex;
    }

    public DocumentAction getDocumentAction() {
        if (actionType != ActionType.DocumentAction)
            return null;

        switch(documentActionType) {
            case ChangeBlockAction:
                return new ChangeBlockAction("", blockIndex, content);
            case AddBlockAction:
                return new AddBlockAction("", blockIndex, content);
            case RemoveBlockAction:
                return new RemoveBlockAction("", blockIndex);
            default:
                throw new IllegalStateException("nieustawione documentActionType");
        }
    }
}
