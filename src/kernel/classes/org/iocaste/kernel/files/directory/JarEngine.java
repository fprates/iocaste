package org.iocaste.kernel.files.directory;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

import org.iocaste.kernel.files.FileServices;
import org.iocaste.protocol.files.DirectoryLeaf;

public class JarEngine implements DirectoryEngine {
    private JarOutputStream jar;
    private DirectoryLeaf root;
    
    @Override
    public final void dir(DirectoryLeaf leaf) throws Exception {
        if (leaf != root)
            jar.putNextEntry(new JarEntry(leaf.getName()));
    }
    
    @Override
    public final void file(DirectoryLeaf leaf) throws Exception {
        jar.putNextEntry(new JarEntry(leaf.getName()));
    }
    
    @Override
    public void start(String path, DirectoryLeaf leaf) throws Exception {
        String target;
        OutputStream os;
        
        root = leaf;
        target = FileServices.composeFileName(path, leaf.getName());
        os = new FileOutputStream(target);
        jar = new JarOutputStream(os);
    }
    
    @Override
    public void write() throws Exception {
        jar.flush();
        jar.close();
    }
}