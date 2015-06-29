package org.iocaste.kernel.files;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;
import org.iocaste.texteditor.common.TextEditorTool;

public class Services extends AbstractFunction {
    public Map<String, Map<String, FileEntry>> entries;
    
    public Services() {
        entries = new HashMap<>();
        export("file", new FileOperations());
        export("mkdir", new MakeDirectory());
        export("write", new FileWrite());
        export("close", new FileClose());
    }
    
    public static final String getPath(String... args) {
        String path = TextEditorTool.composeFileName(
                System.getProperty("user.home"), "iocaste");
        
        if (args != null)
            for (String arg : args)
                if (arg != null)
                    path = TextEditorTool.composeFileName(path, arg);
        
        return path;
    }
    
    public final FileEntry instance(String sessionid, String... args) {
        Map<String, FileEntry> files;
        FileEntry entry;
        
        files = entries.get(sessionid);
        if (files == null) {
            files = new HashMap<>();
            entries.put(sessionid, files);
        }
        
        entry = new FileEntry();
        entry.filename = getPath(args);
        files.put(entry.filename, entry);
        
        return entry;
    }
}

class FileEntry {
    public String filename;
    public SeekableByteChannel channel;
}

class MakeDirectory extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String[] args = message.get("args");
        File file;
        String path = Services.getPath(args);
        
        file = new File(path);
        if (!file.exists())
            file.mkdirs();
        return null;
    }
    
}

class FileOperations extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        FileEntry entry;
        int option = message.geti("option");
        String[] args = message.get("args");
        Services services = getFunction();
        String sessionid = message.getSessionid();
        OpenOption[] options = {
                StandardOpenOption.APPEND,
                StandardOpenOption.CREATE
        };
        Path path;
        
        switch (option) {
        case Iocaste.CREATE:
            entry = services.instance(sessionid, args);
            path = Paths.get(entry.filename);
            entry.channel = Files.newByteChannel(path, options);
            entry = services.instance(sessionid, args);
            return entry.filename;
        }
        
        return null;
    }
    
}

class FileWrite extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String id = message.getString("id");
        byte[] data = message.get("buffer");
        Services services = getFunction();
        String sessionid = message.getSessionid();
        FileEntry entry = services.entries.get(sessionid).get(id);
        ByteBuffer buffer = ByteBuffer.wrap(data);
        
        entry.channel.write(buffer);
        return null;
    }
    
}

class FileClose extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String id = message.getString("id");
        String sessionid = message.getSessionid();
        Services services = getFunction();
        FileEntry entry = services.entries.get(sessionid).get(id);
        
        entry.channel.close();
        
        return null;
    }
    
}