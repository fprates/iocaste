package org.iocaste.kernel.runtime.shell.factories;

import org.iocaste.kernel.runtime.shell.ComponentEntry;
import org.iocaste.kernel.runtime.shell.SpecItemHandler;
import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.runtime.common.application.ContextEntry;

public interface SpecFactory {

    public abstract void generate(ViewContext viewctx, ComponentEntry entry);
    
    public abstract void generate(ViewContext viewctx,
    		ComponentEntry entry, String prefix);
    
    public abstract <T> T get();
    
    public abstract <T extends SpecItemHandler> T getHandler();
    
    public abstract <T extends ContextEntry> T instance(String name);
    
    public abstract void run(ViewContext viewctx,
    		ComponentEntry entry, String prefix);
    
    public abstract void update(ViewContext viewctx, String name);
}
