package org.iocaste.workbench.project.compile;

import org.iocaste.protocol.files.Directory;

public interface ConfigFile {

    public abstract void run(CompileData data);
    
    public abstract void save(Directory war);
}
