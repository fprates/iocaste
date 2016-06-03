package org.iocaste.kernel.files;

import java.io.File;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class DeleteFile extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String[] args = message.get("args");
        boolean all = message.getbool("all");
        return run(args, all);
    }
    
    public Object run(String[] files, boolean all) {
        String path = FileServices.getPath(files);
        File file = new File(path);
        
        if (!all)
            return file.delete();
        
        return recursive(file);
    }
    
    private boolean recursive(File file) {
        File child;
        String[] files = file.list();
        
        if (files == null)
            return file.delete();
        
        for (String name : files) {
            child = new File(name);
            if (child.isDirectory() && !recursive(child))
                return false;
            
            if (!child.delete())
                return false;
        }
        
        return true;
    }
    
}
