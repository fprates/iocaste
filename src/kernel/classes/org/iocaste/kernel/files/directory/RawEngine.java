package org.iocaste.kernel.files.directory;

import java.io.File;

import org.iocaste.kernel.files.DeleteFile;
import org.iocaste.kernel.files.FileClose;
import org.iocaste.kernel.files.FileOperations;
import org.iocaste.kernel.files.FileRead;
import org.iocaste.kernel.files.FileServices;
import org.iocaste.kernel.files.FileWrite;
import org.iocaste.kernel.files.MakeDirectory;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.files.Directory;
import org.iocaste.protocol.files.DirectoryInstance;
import org.iocaste.protocol.files.DirectoryLeaf;

public class RawEngine implements DirectoryEngine {
    private DirectoryLeaf root;
    private FileServices services;
    private FileOperations fop;
    private FileWrite fwrite;
    private FileRead fread;
    private FileClose fclose;
    private String sessionid, path;
    
    public RawEngine(FileServices services) {
        this.services = services;
    }
    
    @Override
    public final void dir(DirectoryLeaf leaf) throws Exception {
        String dirpath;
        
        if (leaf == root)
            return;
        dirpath = FileServices.composeFileName(path, leaf.getPath());
        if (!new File(dirpath).mkdir())
            throw new IocasteException(
                    String.format("error creating %s", dirpath));
    }
    
    @Override
    public final void file(DirectoryLeaf leaf) throws Exception {
        byte[] buffer;
        String fdi, fdo;
        DirectoryInstance instance;
        
        if (fop == null) {
            fop = services.get("file");
            fwrite = services.get("write");
            fread = services.get("read");
            fclose = services.get("close");
        }
        
        instance = leaf.getInstance();
        fdo = fop.createabsolute(services, sessionid,
                FileServices.composeFileName(path, leaf.getPath()));
        switch (instance.getAction()) {
        case DirectoryInstance.BUFFER:
            fwrite.run(services, sessionid, fdo, instance.getContent());
            break;
        case DirectoryInstance.COPY:
            fdi = fop.open(services, sessionid, "r", instance.getSource());
            buffer = fread.run(services, sessionid, fdi);
            fclose.run(services, fdi, sessionid);
            fwrite.run(services, sessionid, fdo, buffer);
            break;
        }
        fclose.run(services, sessionid, fdo);
    }
    
    @Override
    public final void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }
    
    @Override
    public final void start(String path, Directory dir) throws Exception {
        DeleteFile delete = services.get("delete");
        MakeDirectory mkdir = services.get("mkdir");
        
        this.path = path;
        this.root = dir.get();
        
        delete.runabsolute(true, path);
        mkdir.runabsolute(path);
    }
    
    @Override
    public final void write() throws Exception { }
}