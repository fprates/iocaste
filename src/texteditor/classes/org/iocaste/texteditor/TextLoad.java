package org.iocaste.texteditor;

import java.util.Map;
import java.util.TreeMap;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.files.FileEntry;

public class TextLoad extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Map<String, String> pages;
        FileEntry[] files;
        String content;
        String textname = message.get("textname");
        String id = message.getst("id");
        Iocaste iocaste = new Iocaste(getFunction());
        
        if (id != null) {
            content = fileget(iocaste, textname, id);
            if (content == null)
                return null;
            pages = new TreeMap<>();
            pages.put(id, content);
            return pages;
        }
        
        files = iocaste.getFiles("texteditor", textname);
        if (files == null)
            return null;
        pages = new TreeMap<>();
        for (FileEntry file : files)
            pages.put(file.name, fileget(iocaste, textname, file.name));
        return pages;
    }
    
    private final String fileget(
            Iocaste iocaste, String textname, String filename) {
        String fd = iocaste.file(
                Iocaste.READ, "texteditor", textname, filename);
        if (fd == null)
            return null;
        String content = new String(iocaste.read(fd));
        iocaste.close(fd);
        return content;
    }

}
