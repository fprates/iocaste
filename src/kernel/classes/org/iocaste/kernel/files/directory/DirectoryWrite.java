package org.iocaste.kernel.files.directory;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.kernel.files.FileServices;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.files.Directory;
import org.iocaste.protocol.files.DirectoryLeaf;

public class DirectoryWrite extends AbstractHandler {
    private Map<Byte, DirectoryEngine> engines;
    
    public DirectoryWrite() {
        engines = new HashMap<>();
        engines.put(Iocaste.JAR, new JarEngine());
    }
    
    @Override
    public Object run(Message message) throws Exception {
        DirectoryEngine engine;
        String symbol = message.get("symbol");
        Directory root = message.get("directory");
        byte type = message.getb("type");
        
        engine = engines.get(type);
        engine.start(FileServices.getSymbolPath(symbol), root);
        write(root.get(), engine);
        engine.write();
        return null;
    }

    private final void write(DirectoryLeaf leaf, DirectoryEngine engine)
            throws Exception {
        Map<String, DirectoryLeaf> children;
        
        switch (leaf.getType()) {
        case DirectoryLeaf.FILE:
            engine.file(leaf);
            break;
        case DirectoryLeaf.DIR:
            engine.dir(leaf);
            children = leaf.getChildren();
            for (String key : children.keySet()) {
                write(children.get(key), engine);
            }
            break;
        }
    }
}
