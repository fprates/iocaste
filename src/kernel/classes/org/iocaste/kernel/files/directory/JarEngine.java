package org.iocaste.kernel.files.directory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

import org.iocaste.kernel.files.FileServices;
import org.iocaste.protocol.files.Directory;
import org.iocaste.protocol.files.DirectoryLeaf;

public class JarEngine implements DirectoryEngine {
    private JarOutputStream jar;
    private DirectoryLeaf root;
    
    @Override
    public final void dir(DirectoryLeaf leaf) throws Exception {
        if (leaf != root)
            jar.putNextEntry(
                    new JarEntry(leaf.getPath().concat(File.separator)));
    }
    
    @Override
    public final void file(DirectoryLeaf leaf) throws Exception {
        FileChannel channel;
        String file;
        int limit;
        FileInputStream fis;
        ByteBuffer buffer = null;
        
        file = leaf.getPath();
        jar.putNextEntry(new JarEntry(file));
        fis = new FileInputStream(file);
        channel = fis.getChannel();
        
        if (buffer == null)
            buffer = ByteBuffer.allocate(64*1024);
        
        buffer.rewind();
        while ((limit = channel.read(buffer)) > 0) {
            buffer.flip();
            if (buffer.hasArray())
                jar.write(buffer.array(), 0, limit);
            buffer.clear();
        }
        
        channel.close();
        fis.close();
        jar.closeEntry();
    }
    
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