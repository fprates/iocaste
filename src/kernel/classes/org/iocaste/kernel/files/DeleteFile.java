package org.iocaste.kernel.files;

import java.io.File;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class DeleteFile extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String[] args = message.get("args");
        boolean all = message.getbl("all");
        return run(all, args);
    }
    
    public Object run(boolean all, String... files) {
        String path = FileServices.getPath(files);
        return runabsolute(all, path);
    }
    
    public Object runabsolute(boolean all, String path) {
        File file = new File(path);
        return (!all)? file.delete() : recursive(file);
    }
    
    private boolean recursive(File file) {
        File[] files;
        
        if (file.isFile())
            return file.delete();
        
        files = file.listFiles();
        if (files == null)
            return file.delete();
        
        for (File child : files)
            recursive(child);
        
        return file.delete();
    }
    
}
