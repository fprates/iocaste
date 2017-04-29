package org.iocaste.kernel.runtime.shell.factories;

import org.iocaste.runtime.common.application.ContextEntry;
import org.iocaste.runtime.common.application.ToolData;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.kernel.runtime.shell.ComponentEntry;
import org.iocaste.kernel.runtime.shell.SpecItemHandler;
import org.iocaste.kernel.runtime.shell.ViewContext;

public abstract class AbstractSpecFactory implements SpecFactory {
    private SpecItemHandler handler;

    @Override
    public void addEventHandler(ViewContext viewctx, String htmlname) { }
    
    protected void execute(ViewContext viewctx,
    		Container container, String parent, String name) { }

    @Override
    public void generate(ViewContext viewctx,
    		ComponentEntry entry, String prefix) { }
    
    @Override
    public final void generate(ViewContext viewctx, ComponentEntry entry) {
        generate(viewctx, entry, null);
    }
    
    @Override
    public <T> T get() {
        return null;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public final <T extends SpecItemHandler> T getHandler() {
        return (T)handler;
    }
    
    @Override
    public <T extends ContextEntry> T instance(String name) {
    	return null;
    }
    
    @Override
    public final void run(ViewContext viewctx,
    		ComponentEntry entry, String prefix) {
        String name;
        Container container;
        String parent = entry.data.parent;

        if (prefix == null) {
            name = entry.data.name;
            container = viewctx.view.getElement(parent);
        } else {
            name = new StringBuilder(prefix).
            		append("_").append(entry.data.name).toString();
            container = viewctx.view.getElement(parent);
            if (container == null) {
                parent = new StringBuilder(prefix).
                		append("_").append(parent).toString();
                container = viewctx.view.getElement(parent);
            }
            
            entry.data.name = name;
            viewctx.add(entry.data);
        }
        
        if (!entry.data.type.isComposed())
        	execute(viewctx, container, parent, name);
    }
    
    protected final void setHandler(SpecItemHandler handler) {
        this.handler = handler;
    }
    
    @Override
    public void update(ViewContext viewctx, String name) {
    	InputComponent input;
    	ToolData tooldata = viewctx.entries.get(name).data;
    	if (!tooldata.datastore)
    		return;
    	input = viewctx.view.getElement(name);
    	tooldata.value = input.get();
    }

}
