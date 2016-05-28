package org.iocaste.kernel.files.directory;

import org.iocaste.protocol.files.DirectoryLeaf;

public interface DirectoryEngine {
    
    public abstract void dir(DirectoryLeaf leaf) throws Exception;
    
    public abstract void file(DirectoryLeaf leaf) throws Exception;
    
    public abstract void start(String path, DirectoryLeaf leaf)
            throws Exception;
    
    public abstract void write() throws Exception;
}
