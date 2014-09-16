package org.iocaste.texteditor.common;

import java.io.File;
import java.util.Map;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.PackageTool;
import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.TextArea;

public class TextEditorTool extends AbstractServiceInterface {
    private AbstractContext context;
    public TextEditorTool tetool;
    
    public TextEditorTool(Function function) {
        initService(function, InstallData.TXTEDITOR_SERVERNAME);
    }
    
    public TextEditorTool(AbstractContext context) {
        initService(context.function, InstallData.TXTEDITOR_SERVERNAME);
        this.context = context;
    }
    
    public final void commit(TextEditor editor, String id) {
        TextArea textarea = context.view.getElement(editor.getName());
        editor.commit(id, textarea.getst());
    }

    public static final String composeFileName(String... names) {
        StringBuilder sb = new StringBuilder();
        
        for (String name : names) {
            if (sb.length() > 0)
                sb.append(File.separator);
        
            sb.append(name);
        }
        
        return sb.toString();
    }
    
    public final Map<String, String> get(String textnm, String id) {
        Message message = new Message("load");
        message.add("textname", textnm);
        message.add("id", id);
        return call(message);
    }
    
    public final TextEditor instance(Container container, String name) {
        TextEditor editor = new TextEditor(name);
        
        editor.setElement(new TextArea(container, name));
        return editor;
    }
    
    public final void load(TextEditor editor, String textnm, String id) {
        InputComponent input;
        Map<String, String> pages = get(textnm, id);
        
        if (pages == null)
            return;
        
        for (String key : pages.keySet())
            editor.commit(key, pages.get(key));
        
        if (id == null)
            return;
        
        input = context.view.getElement(editor.getName());
        input.set(pages.get(id));
    }
    
    public final void register(String name) {
        String app;
        Iocaste iocaste = new Iocaste(context.function);
        InstallData data = new InstallData();
        
        data.addText(name);
        app = iocaste.getCurrentApp();
        new PackageTool(context.function).install(data, app);
    }
    
    public static final void removeCompleteDir(String dir) {
        File origin = new File(dir);
        File[] files = origin.listFiles();
        
        if (files != null)
            for (File file : files) {
                if (file.isDirectory())
                    removeCompleteDir(file.getAbsolutePath());
                file.delete();
            }
        
        origin.delete();
    }
    
    public final void set(TextEditor editor, String id, String text) {
        TextArea textarea = context.view.getElement(editor.getName());
        textarea.set(text);
        editor.commit(id, text);
    }
    
    public final void setEnabled(TextEditor editor, boolean enabled) {
        context.view.getElement(editor.getName()).setEnabled(enabled);
    }
    
    public final void update(TextEditor editor, String textnm) {
        Message message = new Message("update");
        message.add("textname", textnm);
        message.add("editor", editor);
        call(message);
    }
    
    public final void update(String textobj, String page, String text) {
        Message message = new Message("update_text");
        message.add("textobj", textobj);
        message.add("id", page);
        message.add("text", text);
        call(message);
    }
}
