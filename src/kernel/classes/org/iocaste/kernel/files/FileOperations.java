package org.iocaste.kernel.files;

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

    @Override
    public Object run(Message message) throws Exception {
        int option = message.geti("option");
        String[] args = message.get("args");
        String sessionid = message.getSessionid();
        FileServices services = getFunction();
        
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
    
    public final String create(FileServices services, String sessionid,
            String... args) throws Exception {
        InternalFileEntry entry;
        Path path;
        OpenOption[] options = {
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        };
        
        entry = services.instance(sessionid, args);
        path = Paths.get(entry.filename);
        entry.channel = Files.newByteChannel(path, options);
        return entry.filename;
    }
    
    public final String open(FileServices services, String sessionid,
            String mode, String... args) throws Exception {
        InternalFileEntry entry;

        entry = services.instance(sessionid, args);
        entry.file = new RandomAccessFile(entry.filename, mode);
        entry.fchannel = entry.file.getChannel();
        return entry.filename;
    }
}
