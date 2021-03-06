package org.iocaste.kernel.files;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.util.HashMap;
import java.util.Map;

import org.iocaste.kernel.files.directory.DirectoryWrite;
import org.iocaste.protocol.AbstractFunction;

public class FileServices extends AbstractFunction {
    public Map<String, Map<String, InternalFileEntry>> entries;
    
    public FileServices() {
        entries = new HashMap<>();
        export("file_close", new FileClose());
        export("file_delete", new DeleteFile());
        export("directory_write", new DirectoryWrite(this));
        export("file_entries_delete", new FileEntriesDelete());
        export("file", new FileOperations());
        export("file_exists", new FileExists());
        export("files_get", new GetFiles());
        export("mkdir", new MakeDirectory());
        export("file_unzip", new Unzip());
        export("file_read", new FileRead());
        export("file_write", new FileWrite());
    }

    public static final String composeFileName(String... names) {
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
    
    public static final String getSymbolPath(String symbol, String... args) {
        switch (symbol) {
        case "WEBAPPS":
            return composeFileName(
                    File.separator, "var", "lib", "tomcat", "webapps");
        case "WORKBENCH_LIBS":
            return composeFileName(getSymbolPath("WEBAPPS"),
                    "iocaste-workbench", "WEB-INF", "lib");
        case "JAVA_SOURCE":
            return composeFileName(getJavaPath("src", args));
        case "JAVA_BIN":
            return composeFileName(getJavaPath("bin", args));
        case "FULL_JAVA_SOURCE":
            return getPath(getSymbolPath("JAVA_SOURCE", args));
        case "FULL_JAVA_BIN":
            return getPath(getSymbolPath("JAVA_BIN", args));
        default:
            return null;
        }
    }
    
    private static final String[] getJavaPath(String prefix, String... args) {
        String[] fullargs;
        
        fullargs = new String[args.length + 2];
        fullargs[0] = "kernel";
        for (int i = 0; i < args.length; i++)
            fullargs[i+1] = args[i];
        fullargs[fullargs.length - 1] = prefix;
        return fullargs;
    }
    
    public final InternalFileEntry instance(String sessionid, String path) {
        Map<String, InternalFileEntry> files;
        InternalFileEntry entry;
        
        files = entries.get(sessionid);
        if (files == null) {
            files = new HashMap<>();
            entries.put(sessionid, files);
        }
        
        entry = new InternalFileEntry();
        entry.filename = path;
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
