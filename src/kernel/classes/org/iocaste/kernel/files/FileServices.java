package org.iocaste.kernel.files;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.util.HashMap;
import java.util.Map;

import org.iocaste.protocol.AbstractFunction;

public class FileServices extends AbstractFunction {
    public Map<String, Map<String, InternalFileEntry>> entries;
    
    public FileServices() {
        entries = new HashMap<>();
        export("close", new FileClose());
        export("delete", new DeleteFile());
        export("file", new FileOperations());
        export("file_exists", new FileExists());
        export("files_get", new GetFiles());
        export("mkdir", new MakeDirectory());
        export("unzip", new Unzip());
        export("read", new FileRead());
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
    
    public final InternalFileEntry instance(String sessionid, String... args) {
        Map<String, InternalFileEntry> files;
        InternalFileEntry entry;
        
        files = entries.get(sessionid);
        if (files == null) {
            files = new HashMap<>();
            entries.put(sessionid, files);
        }
        
        entry = new InternalFileEntry();
        entry.filename = getPath(args);
        files.put(entry.filename, entry);
        
        return entry;
    }
}

class InternalFileEntry {
    public String filename;
    public SeekableByteChannel channel;
    public FileChannel fchannel;
    public RandomAccessFile file;
}
