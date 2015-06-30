package org.iocaste.kernel.files;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.files.UnzippedEntry;

public class FileServices extends AbstractFunction {
    public Map<String, Map<String, FileEntry>> entries;
    
    public FileServices() {
        entries = new HashMap<>();
        export("file", new FileOperations());
        export("mkdir", new MakeDirectory());
        export("write", new FileWrite());
        export("close", new FileClose());
        export("unzip", new Unzip());
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

class Unzip extends AbstractHandler {
    private static final int BUFFER_SIZE = 64 * 1024;

    @Override
    public Object run(Message message) throws Exception {
        ZipEntry zipentry;
        ZipInputStream zis;
        FileInputStream fis;
        File file;
        String id, filename;
        List<UnzippedEntry> entries;
        UnzippedEntry entry;
        String sessionid = message.getSessionid();
        String target = message.get("target");
        String[] source = message.get("source");
        byte[] buffer = new byte[BUFFER_SIZE];
        FileServices services = getFunction();
        FileClose close = services.get("close");
        FileOperations fileop = services.get("file");
        FileWrite write = services.get("write");
        MakeDirectory mkdir = services.get("mkdir");
        
        filename = FileServices.getPath(source);
        file = new File(filename);
        fis = new FileInputStream(file);
        zis = new ZipInputStream(new BufferedInputStream(fis));
        entries = null;
        while ((zipentry = zis.getNextEntry()) != null) {
            if (entries == null)
                entries = new ArrayList<>();
            
            entry = new UnzippedEntry(entries, zipentry);
            if (entry.directory) {
                mkdir.run(target, entry.name);
                continue;
            }
            
            id = fileop.create(services, sessionid, target, entry.name);
            while ((zis.read(buffer, 0, BUFFER_SIZE)) > 0)
                write.run(services, sessionid, id, buffer);
            close.run(services, sessionid, id);
        }
        
        fis.close();
        file.delete();
        return entries;
    }
    
}