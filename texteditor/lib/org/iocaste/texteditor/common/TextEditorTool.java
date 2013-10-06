package org.iocaste.texteditor.common;

import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.PageContext;
import org.iocaste.shell.common.TextArea;

public class TextEditorTool extends AbstractServiceInterface {
    private static final String SERVER_NAME =
            "/iocaste-texteditor/services.html";
    private PageContext context;
    public TextEditorTool tetool;
    
    public TextEditorTool(PageContext context) {
        initService(context.function, SERVER_NAME);
        this.context = context;
    }
    
    public final void commit(String page, TextEditor editor) {
        TextArea textarea = context.view.getElement(editor.getName());
        editor.commit(page, (String)textarea.get());
    }
    
    public final TextEditor instance(Container container, String name) {
        new TextArea(container, name);
        
        return new TextEditor(name);
    }
    
    public final void save(String id, TextEditor editor) {
        Message message = new Message();
        
        message.setId("save");
        message.add("id", id);
        message.add("editor", editor);
        call(message);
    }
}
