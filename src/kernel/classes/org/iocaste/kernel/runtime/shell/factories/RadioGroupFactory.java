package org.iocaste.kernel.runtime.shell.factories;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.kernel.runtime.shell.elements.RadioGroup;
import org.iocaste.runtime.common.application.ToolData;
import org.iocaste.shell.common.Container;

public class RadioGroupFactory extends AbstractSpecFactory {

    @Override
    protected void execute(ViewContext viewctx,
    		Container container, String parent, String name) {
        new RadioGroup(viewctx, name);
    }
    
    @Override
    public final void update(ViewContext viewctx, String name) {
    	ToolData tooldata = viewctx.entries.get(name).data;
    	if (tooldata.value != null)
    		tooldata.value = ((String)tooldata.value).split("\\.")[1];
    }

}
