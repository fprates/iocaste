package org.iocaste.transport;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.texteditor.common.TextEditorTool;

public class Services extends AbstractFunction {
    public Map<String, TransportEntry> transfers;
    
    public Services() {
        export("cancel", new CancelTransport());
        export("finish", new FinishTransport());
        export("send", new SendTransport());
        export("start", new StartTransport());
        transfers = new HashMap<>();
    }
    
    public final void instance(String filename) throws Exception {
        Path path;
        OpenOption[] options = {
                StandardOpenOption.APPEND,
                StandardOpenOption.CREATE
        };
        TransportEntry entry = new TransportEntry();
        
        mkdir();
        
        path = Paths.get(filename);
        entry.filename = getPath(filename);
        entry.channel = Files.newByteChannel(path, options);
        transfers.put(filename, entry);
    }
    
    private final String getPath(String... args) {
        String path = TextEditorTool.composeFileName(
                System.getProperty("user.home"), "iocaste", "transport");
        
        if (args != null)
            for (String arg : args)
                if (arg != null)
                    path = TextEditorTool.composeFileName(path, arg);
        
        return path;
    }
    
    private final void mkdir() throws Exception {
        File file;
        String path = getPath();
        
        file = new File(path);
        if (!file.exists())
            file.mkdirs();
    }
}
