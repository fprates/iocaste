package org.iocaste.protocol.files;

import java.io.Serializable;
import java.util.List;
import java.util.zip.ZipEntry;

public class UnzippedEntry implements Serializable {
    private static final long serialVersionUID = 5864936999080866305L;
    public String name;
    public boolean directory;
    
    public UnzippedEntry(List<UnzippedEntry> entries, ZipEntry zipentry) {
        entries.add(this);
        name = zipentry.getName();
        directory = zipentry.isDirectory();
    }
}
