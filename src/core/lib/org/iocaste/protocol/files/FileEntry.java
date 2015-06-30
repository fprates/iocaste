package org.iocaste.protocol.files;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.zip.ZipEntry;

public class FileEntry implements Serializable {
    private static final long serialVersionUID = 5864936999080866305L;
    public String name;
    public boolean directory;
    
    public FileEntry(List<FileEntry> entries, ZipEntry zipentry) {
        entries.add(this);
        name = zipentry.getName();
        directory = zipentry.isDirectory();
    }
    
    public FileEntry(List<FileEntry> entries, File file) {
        entries.add(this);
        name = file.getName();
        directory = file.isDirectory();
    }
}
