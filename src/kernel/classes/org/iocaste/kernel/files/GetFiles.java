package org.iocaste.kernel.files;

import java.io.File;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.files.FileEntry;

public class GetFiles extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String[] args = message.get("args");
        return run(args);
    }
    
    public FileEntry[] run(String... args) throws Exception {
        FileEntry[] files;
        File[] list;
        String path = FileServices.getPath(args);
        File file = new File(path);
        
        if (!file.isDirectory())
            return null;
        
        list = file.listFiles();
        if ((list == null) || (list.length == 0))
            return null;
        
        files = new FileEntry[list.length];
        for (int i = 0; i < list.length; i++)
            files[i] = new FileEntry(
                    list[i].getName(), args, list[i].isDirectory());
        
        return files;
    }
    
}
