package org.iocaste.texteditor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.TreeMap;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;
import org.iocaste.texteditor.common.TextEditor;
import org.iocaste.texteditor.common.TextEditorTool;

public class Services extends AbstractFunction {

    public Services() {
        export("load", "load");
        export("register", "register");
        export("update", "update");
        export("update_text", "updateText");
        export("unregister", "unregister");
    }
    
    private final String fileread(File file) throws Exception {
        byte[] buffer = new byte[((Number)file.length()).intValue()];
        InputStream is = new FileInputStream(file);
        is.read(buffer);
        is.close();
        return new String(buffer);
    }
    
    private final String getPath(String... args) {
        String path = TextEditorTool.composeFileName(
                System.getProperty("user.home"), "iocaste", "texteditor");
        
        for (String arg : args)
            if (arg != null)
                path = TextEditorTool.composeFileName(path, arg);
        
        return path;
    }
    
    public final Map<String, String> load(Message message) throws Exception {
        File file;
        Map<String, String> pages;
        String textname = message.get("textname");
        String id = message.getst("id");
        String path = getPath(textname, id);
            
        file = new File(path);
        if (!file.exists())
            return null;
        
        pages = new TreeMap<>();
        if (file.isDirectory())
            for (String name : file.list()) {
                file = new File(getPath(textname, id, name));
                pages.put(name, fileread(file));
            }
        else
            pages.put(id, fileread(file));
        
        return pages;
    }
    
    public final void register(Message message) throws Exception {
        File file;
        String name = message.get("name");
        String path = getPath(name);
        
        file = new File(path);
        if (!file.exists())
            file.mkdirs();
        
        for (File arq : file.listFiles())
            arq.delete();
    }
    
    public final void unregister(Message message) {
        String name = message.get("name");
        String path = getPath(name);
        TextEditorTool.removeCompleteDir(path);
    }
    
    public final void update(Message message) throws Exception {
        String textname = message.get("textname");
        TextEditor editor = message.get("editor");
        
        for (String id : editor.getPages())
            update(textname, id, editor.getString(id));
    }
    
    private final void update(String textname, String id, String text)
            throws Exception {
        OutputStream os;
        String path = getPath(textname, id);
        File file = new File(path);
        
        if (!file.exists())
            file.createNewFile();
        
        if (text == null)
            text = "";
        
        os = new FileOutputStream(file);
        os.write(text.getBytes());
        os.close();
    }
    
    public final void updateText(Message message) throws Exception {
        String textobj = message.get("textobj");
        String id = message.get("id");
        String text = message.get("text");
        
        update(textobj, id, text);
    }
}
