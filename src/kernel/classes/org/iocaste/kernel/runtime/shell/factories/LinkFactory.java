package org.iocaste.kernel.runtime.shell.factories;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Link;

public class LinkFactory extends AbstractSpecFactory {

    @Override
    protected void execute(ViewContext viewctx,
    		Container container, String parent, String name) {
        Link link = new Link(container, name, name);
        String action = link.getAction();
        
        if (!link.isAbsolute() && (action != null))
            viewctx.addEventHandler("click", action);
        for (String key : link.getEvents().keySet())
        	viewctx.addEventHandler(key, action);
    }

}
