package org.iocaste.protocol.files;

import java.io.Serializable;

public class FileEntry implements Serializable {
    private static final long serialVersionUID = 5864936999080866305L;
    public String name;
    public boolean directory;
    public String[] path;
    
    public FileEntry(String[] path, boolean directory) {
        this.name = path[path.length - 1];
        this.directory = directory;
        this.path = path;
    }
    
    public FileEntry(String file, String[] path, boolean directory)
            throws Exception {
        this.path = new String[path.length + 1];
        this.name = file;
        this.directory = directory;
        for (int i = 0; i < path.length; i++)
            this.path[i] = path[i];
        this.path[path.length] = file;
    }
}
