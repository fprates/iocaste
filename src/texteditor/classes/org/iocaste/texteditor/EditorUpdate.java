package org.iocaste.texteditor;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;
import org.iocaste.texteditor.common.TextEditor;

public class EditorUpdate extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String textname = message.get("textname");
        TextEditor editor = message.get("editor");
        Iocaste iocaste = new Iocaste(getFunction());
        for (String id : editor.getPages())
            Services.update(iocaste, textname, id, editor.getString(id));
        return null;
    }

}
