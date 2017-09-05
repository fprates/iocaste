package org.iocaste.kernel.files;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;

public class FileOperations extends AbstractHandler {
    
    public final String create(FileServices services, String sessionid,
            String... args) throws Exception {
        return createabsolute(services, sessionid, FileServices.getPath(args));
    }
    
    public final String createabsolute(FileServices services, String sessionid,
            String abspath) throws Exception {
        InternalFileEntry entry;
        Path path;
        OpenOption[] options = {
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        };
        
        entry = services.instance(sessionid, abspath);
        path = Paths.get(entry.filename);
        entry.channel = Files.newByteChannel(path, options);
        return entry.filename;
    }
    
    public final String open(FileServices services, String sessionid,
            String mode, String... args) throws Exception {
        return openabsolute(
                services, sessionid, mode, FileServices.getPath(args));
    }
    
    @SuppressWarnings("resource")
    public final String openabsolute(FileServices services, String sessionid,
            String mode, String path) throws Exception {
        InternalFileEntry entry;
        RandomAccessFile file;

        try {
            file = new RandomAccessFile(path, mode);
        } catch (FileNotFoundException e) {
            return null;
        }
        
        entry = services.instance(sessionid, path);
        entry.file = file;
        entry.fchannel = entry.file.getChannel();
        return entry.filename;
    }

    @Override
    public Object run(Message message) throws Exception {
        int option = message.geti("option");
        String[] args = message.get("args");
        String sessionid = message.getSessionid();
        
        return run(getFunction(), sessionid, option, args);
    }

    public String run(FileServices services,
            String sessionid, int option, String[] args) throws Exception {
        switch (option) {
        case Iocaste.CREATE:
            return create(services, sessionid, args);
        case Iocaste.READ:
            return open(services, sessionid, "r", args);
        case Iocaste.WRITE:
            return open(services, sessionid, "rw", args);
        }
        
        return null;
    }
}
