package org.iocaste.kernel.files;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.files.FileEntry;

public class Unzip extends AbstractHandler {
    private static final int BUFFER_SIZE = 64 * 1024;

    @Override
    public Object run(Message message) throws Exception {
        ZipEntry zipentry;
        ZipInputStream zis;
        FileInputStream fis;
        File file;
        String id, filename;
        List<FileEntry> entries;
        FileEntry entry;
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
            
            entry = new FileEntry(
                    zipentry.getName(), source, zipentry.isDirectory());
            entries.add(entry);
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
        return entries.toArray(new FileEntry[0]);
    }
    
}