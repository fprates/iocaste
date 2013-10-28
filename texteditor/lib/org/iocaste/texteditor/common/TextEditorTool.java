package org.iocaste.texteditor.common;

import java.util.Map;

import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.InputComponent;
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
    
    public final void commit(TextEditor editor, String page) {
        TextArea textarea = context.view.getElement(editor.getName());
        editor.commit(page, (String)textarea.get());
    }
    
    public final TextEditor instance(Container container, String name) {
        new TextArea(container, name);
        
        return new TextEditor(name);
    }
    
    public final void load(TextEditor editor, String textnm, String pagenm) {
        InputComponent input;
        Map<String, String> pages;
        Message message = new Message();
        
        message.setId("load");
        message.add("textname", textnm);
        message.add("pagename", pagenm);
        pages = call(message);
        
        if (pages == null)
            return;
        
        for (String name : pages.keySet())
            editor.commit(name, pages.get(name));
        
        if (pagenm == null)
            return;
        
        input = context.view.getElement(editor.getName());
        input.set(pages.get(pagenm));
    }
    
    public final void save(TextEditor editor, String textnm) {
        Message message = new Message();
        
        message.setId("save");
        message.add("textname", textnm);
        message.add("editor", editor);
        call(message);
    }
    
    public final void set(TextEditor editor, String page, String text) {
        TextArea textarea = context.view.getElement(editor.getName());
        textarea.set(text);
        editor.commit(page, text);
    }
    
    public final void setEnabled(TextEditor editor, boolean enabled) {
        context.view.getElement(editor.getName()).setEnabled(enabled);
    }
}
