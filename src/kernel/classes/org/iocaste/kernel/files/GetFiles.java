package org.iocaste.kernel.files;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.files.FileEntry;

public class GetFiles extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        List<FileEntry> files;
        String[] list;
        String[] args = message.get("args");
        String path = FileServices.getPath(args);
        File file = new File(path);
        
        if (!file.isDirectory())
            return null;
        
        list = file.list();
        if (list == null)
            return null;
        
        files = new ArrayList<>();
        for (String name : list) {
            file = new File(name);
            new FileEntry(files, file);
        }
        
        return files;
    }
    
}
