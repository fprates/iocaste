package org.iocaste.kernel.files;

import java.io.File;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.files.FileEntry;

public class GetFiles extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        FileEntry[] files;
        String[] list;
        String[] args = message.get("args");
        String path = FileServices.getPath(args);
        File file = new File(path);
        
        if (!file.isDirectory())
            return null;
        
        list = file.list();
        if ((list == null) || (list.length == 0))
            return null;
        
        files = new FileEntry[list.length];
        for (int i = 0; i < list.length; i++) {
            file = new File(list[i]);
            files[i] = new FileEntry(list[i], args, file.isDirectory());
        }
        
        return files;
    }
    
}
