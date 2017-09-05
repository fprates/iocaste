package org.iocaste.kernel.texts;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.texteditor.common.TextEditor;

public class EditorUpdate extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String textname = message.get("textname");
        TextEditor editor = message.get("editor");
        String sessionid = message.getSessionid();
        Texts texts = getFunction();
        for (String id : editor.getPages())
            texts.update(sessionid, textname, id, editor.getString(id));
        return null;
    }

}
