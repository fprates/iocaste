package org.iocaste.texteditor.common;

import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.TextArea;

public class TextEditorTool extends AbstractServiceInterface {
    private static final String SERVER_NAME =
            "/iocaste-texteditor/services.html";

    public TextEditorTool(Function function) {
        initService(function, SERVER_NAME);
    }
    
    public final TextEditor instance(Container container, String name) {
        TextArea textarea = new TextArea(container, name);
        
        return new TextEditor(textarea);
    }
    
    public final void save(TextEditor editor) {
        Message message = new Message();
        
        message.setId("save");
        message.add("editor", editor);
        call(message);
    }
}
