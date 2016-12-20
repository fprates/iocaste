package org.iocaste.packagetool.services.installers;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.services.State;
import org.iocaste.protocol.Function;

public interface ModuleInstaller {
    public enum MODES {
        INSTALL,
        UNINSTALL,
        UPDATE
    };
    
    public abstract void init(Function function);
    
    public abstract void install(State state) throws Exception;
    
    public abstract void remove(ExtendedObject object);
    
    public abstract void update(State state) throws Exception;
}

