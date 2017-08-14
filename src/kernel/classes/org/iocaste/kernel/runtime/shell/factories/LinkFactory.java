package org.iocaste.kernel.runtime.shell.factories;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.kernel.runtime.shell.elements.Link;
import org.iocaste.shell.common.Container;

public class LinkFactory extends AbstractSpecFactory {

    @Override
    public final void addEventHandler(ViewContext viewctx, String htmlname) {
        Link link = viewctx.view.getElement(htmlname);
        String action = link.getAction();
        String name = link.getName();
        
        if (!link.isAbsolute() && (action != null))
            viewctx.addEventHandler(name, action, "click");
        for (String key : link.getEvents().keySet())
            viewctx.addEventHandler(name, action, key);
    }
    
    @Override
    protected void execute(ViewContext viewctx,
    		Container container, String parent, String name) {
        new Link(viewctx, name);
    }

}
