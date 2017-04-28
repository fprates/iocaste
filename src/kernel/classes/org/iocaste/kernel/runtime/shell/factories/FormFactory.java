package org.iocaste.kernel.runtime.shell.factories;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.kernel.runtime.shell.renderer.internal.ActionEventHandler;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Form;

public class FormFactory extends AbstractSpecFactory {

    @Override
    protected void execute(ViewContext viewctx,
    		Container container, String parent, String name) {
    	ActionEventHandler handler;
    	String currentaction;
        Form form = new Form(viewctx.view, name);
        
        viewctx.addEventHandler("", currentaction = form.getAction());
        handler = viewctx.getEventHandler(currentaction, "");
        handler.name = currentaction;
        handler.submit = true;
    }

}
