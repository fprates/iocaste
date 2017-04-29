package org.iocaste.kernel.runtime.shell.factories;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.kernel.runtime.shell.renderer.internal.ActionEventHandler;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Form;

public class FormFactory extends AbstractSpecFactory {

    @Override
    public final void addEventHandler(ViewContext viewctx, String htmlname) {
        ActionEventHandler handler;
        String currentaction;
        Form form = viewctx.view.getElement(htmlname);
        
        viewctx.addEventHandler("", currentaction = form.getAction());
        handler = viewctx.getEventHandler(currentaction, "");
        handler.name = currentaction;
        handler.submit = true;
    }
    
    @Override
    protected void execute(ViewContext viewctx,
    		Container container, String parent, String name) {
        new Form(viewctx.view, name);
    }

}
