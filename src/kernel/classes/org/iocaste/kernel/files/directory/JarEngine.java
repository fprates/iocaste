package org.iocaste.kernel.files.directory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

import org.iocaste.kernel.files.FileServices;
import org.iocaste.protocol.files.Directory;
import org.iocaste.protocol.files.DirectoryInstance;
import org.iocaste.protocol.files.DirectoryLeaf;

public class JarEngine implements DirectoryEngine {
    private JarOutputStream jar;
    private DirectoryLeaf root;
    private Map<Byte, CopyHandler> handlers;
    
    public JarEngine() {
        handlers = new HashMap<>();
        new FileCopy(handlers);
        new DirCopy(handlers);
    }
    
    private final void copy(DirectoryInstance instance, String parent)
            throws Exception {
        String file;
        CopyHandler copyhandler;
        
        file = FileServices.composeFileName(instance.getSource());
        file = FileServices.composeFileName(
                FileServices.getSymbolPath(instance.getSourceSymbol()),
                file);
        
        copyhandler = handlers.get(instance.getSourceType());
        copyhandler.setOutputStream(jar);
        copyhandler.execute(instance, parent, file);
    }
    
    @Override
    public final void dir(DirectoryLeaf leaf) throws Exception {
        String parent;
        DirectoryInstance instance;
        
        if (leaf == root)
            return;
        instance = leaf.getInstance();
        if (instance == null) {
            jar.putNextEntry(new JarEntry(leaf.getPath()));
            return;
        }
        parent = leaf.getPath();
        switch (instance.getAction()) {
        case DirectoryInstance.COPY:
            copy(instance, parent);
            break;
        }
        jar.closeEntry();
    }
    
    @Override
    public final void file(DirectoryLeaf leaf) throws Exception {
        String parent;
        DirectoryInstance instance;
        
        parent = leaf.getPath();
        instance = leaf.getInstance();
        switch (instance.getAction()) {
        case DirectoryInstance.BUFFER:
            jar.putNextEntry(new JarEntry(parent));
            jar.write(instance.getContent());
            break;
        case DirectoryInstance.COPY:
            copy(instance, parent);
            break;
        }
        jar.closeEntry();
    }
    
    @Override
    public final void setSessionid(String sessionid) { }
    
    @Override
    public void start(String path, Directory dir) throws Exception {
        String target;
        OutputStream os;
        
        root = dir.get();
        target = FileServices.composeFileName(path, dir.getName());
        os = new FileOutputStream(target);
        jar = new JarOutputStream(os);
    }
    
    @Override
    public void write() throws Exception {
        jar.flush();
        jar.close();
    }
}

interface CopyHandler {
    
    public abstract void execute(DirectoryInstance instance,
            String parent, String file) throws Exception;
    
    public abstract void setOutputStream(JarOutputStream jar);
    
}

class DirCopy implements CopyHandler {
    private JarOutputStream jar;
    private Map<Byte, CopyHandler> handlers;
    private CopyHandler filecopy;
    
    public DirCopy(Map<Byte, CopyHandler> handlers) {
        this.handlers = handlers;
        handlers.put(DirectoryInstance.DIR, this);
    }
    
    @Override
    public final void execute(DirectoryInstance instance,
            String parent, String filename) throws Exception {
        String name;
        File root = new File(filename);
        File[] files = root.listFiles();

        jar.putNextEntry(new JarEntry(parent.concat(File.separator)));
        for (File file : files) {
            name = FileServices.composeFileName(parent, file.getName());
            if (file.isDirectory()) {
                execute(instance, name, file.getCanonicalPath());
                continue;
            }
            filecopy(instance, name, file.getCanonicalPath());
        }
    }
    
    private final void filecopy(DirectoryInstance instance,
            String parent, String filename) throws Exception {
        if (filecopy == null)
            filecopy = handlers.get(DirectoryInstance.FILE);
        filecopy.setOutputStream(jar);
        filecopy.execute(instance, parent, filename);
    }
    
    @Override
    public final void setOutputStream(JarOutputStream jar) {
        this.jar = jar;
    }
}

class FileCopy implements CopyHandler {
    private JarOutputStream jar;
    
    public FileCopy(Map<Byte, CopyHandler> handlers) {
        handlers.put(DirectoryInstance.FILE, this);
    }
    
    @Override
    public final void execute(DirectoryInstance instance,
            String parent, String file) throws Exception {
        int limit;
        FileInputStream fis = new FileInputStream(file);
        FileChannel channel = fis.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(64*1024);

        jar.putNextEntry(new JarEntry(parent));
        buffer.rewind();
        while ((limit = channel.read(buffer)) > 0) {
            buffer.flip();
            if (buffer.hasArray())
                jar.write(buffer.array(), 0, limit);
            buffer.clear();
        }
        
        channel.close();
        fis.close();
    }
    
    @Override
    public final void setOutputStream(JarOutputStream jar) {
        this.jar = jar;
    }
}