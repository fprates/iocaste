package org.iocaste.texteditor.common;

import java.util.Map;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.PageContext;
import org.iocaste.shell.common.TextArea;

public class TextEditorTool extends AbstractServiceInterface {
    private PageContext context;
    public TextEditorTool tetool;
    
    public TextEditorTool(PageContext context) {
        initService(context.function, InstallData.TXTEDITOR_SERVERNAME);
        this.context = context;
    }
    
    public final void commit(TextEditor editor, long page) {
        TextArea textarea = context.view.getElement(editor.getName());
        editor.commit(page, (String)textarea.get());
    }
    
    public final TextEditor instance(Container container, String name) {
        TextEditor editor = new TextEditor(name);
        
        editor.setElement(new TextArea(container, name));
        return editor;
    }
    
    public final Map<Long, String> get(String textnm, long page) {
        Message message = new Message();
        
        message.setId("load");
        message.add("textname", textnm);
        message.add("pagenr", page);
        return call(message);
    }
    
    public final void load(TextEditor editor, String textnm, long page) {
        InputComponent input;
        Map<Long, String> pages = get(textnm, page);
        
        if (pages == null)
            return;
        
        for (long pagenr : pages.keySet())
            editor.commit(pagenr, pages.get(pagenr));
        
        if (page == 0)
            return;
        
        input = context.view.getElement(editor.getName());
        input.set(pages.get(page));
    }
    
    public final void set(TextEditor editor, int page, String text) {
        TextArea textarea = context.view.getElement(editor.getName());
        textarea.set(text);
        editor.commit(page, text);
    }
    
    public final void setEnabled(TextEditor editor, boolean enabled) {
        context.view.getElement(editor.getName()).setEnabled(enabled);
    }
    
    public final void update(TextEditor editor, String textnm) {
        Message message = new Message();
        
        message.setId("update");
        message.add("textname", textnm);
        message.add("editor", editor);
        call(message);
    }
}
