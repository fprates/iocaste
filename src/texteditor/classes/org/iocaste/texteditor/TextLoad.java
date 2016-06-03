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
        String fd;
        String textname = message.get("textname");
        String id = message.getst("id");
        Iocaste iocaste = new Iocaste(getFunction());
        
        files = iocaste.getFiles("texteditor", textname, id);
        if (files == null)
            return null;
        
        pages = new TreeMap<>();
        for (FileEntry file : files) {
            fd = iocaste.file(Iocaste.READ, file.path);
            pages.put(file.name, new String(iocaste.read(fd)));
            iocaste.close(fd);
        }
        return pages;
    }

}
