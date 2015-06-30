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

public class FileServices extends AbstractFunction {
    public Map<String, Map<String, FileEntry>> entries;
    
    public FileServices() {
        entries = new HashMap<>();
        export("close", new FileClose());
        export("file", new FileOperations());
        export("mkdir", new MakeDirectory());
        export("rmdir", new RemoveDirectory());
        export("unzip", new Unzip());
        export("write", new FileWrite());
    }

    private static final String composeFileName(String... names) {
        StringBuilder sb = new StringBuilder();
        
        for (String name : names) {
            if (sb.length() > 0)
                sb.append(File.separator);
        
            sb.append(name);
        }
        
        return sb.toString();
    }
    
    public static final String getPath(String... args) {
        String path = composeFileName(
                System.getProperty("user.home"), "iocaste");
        
        if (args != null)
            for (String arg : args)
                if (arg != null)
                    path = composeFileName(path, arg);
        
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
        
        run(args);
        return null;
    }
    
    public final void run(String... args) {
        File file;
        String path = FileServices.getPath(args);
        
        file = new File(path);
        if (!file.exists())
            file.mkdirs();
    }
    
}

class FileOperations extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        int option = message.geti("option");
        String[] args = message.get("args");
        String sessionid = message.getSessionid();
        FileServices services = getFunction();
        
        switch (option) {
        case Iocaste.CREATE:
            return create(services, sessionid, args);
        }
        
        return null;
    }
    
    public final String create(FileServices services, String sessionid,
            String... args) throws Exception {
        FileEntry entry;
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
}

class FileWrite extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String id = message.getString("id");
        String sessionid = message.getSessionid();
        byte[] data = message.get("buffer");
        FileServices services = getFunction();
        
        run(services, sessionid, id, data);
        return null;
    }
    
    public final void run(FileServices services, String sessionid, String id,
            byte[] data) throws Exception {
        FileEntry entry = services.entries.get(sessionid).get(id);
        ByteBuffer buffer = ByteBuffer.wrap(data);

        entry.channel.write(buffer);
    }
    
}

class FileClose extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String id = message.getString("id");
        String sessionid = message.getSessionid();
        FileServices services = getFunction();
        
        run(services, sessionid, id);
        
        return null;
    }
    
    public final void run(FileServices services, String sessionid, String id)
            throws Exception {
        Map<String, FileEntry> files = services.entries.get(sessionid);
        FileEntry entry = files.get(id);
        
        entry.channel.close();
        files.remove(id);
    }
}

class RemoveDirectory extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String[] args = message.get("args");
        boolean all = message.getbool("all");
        String path = FileServices.getPath(args);
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