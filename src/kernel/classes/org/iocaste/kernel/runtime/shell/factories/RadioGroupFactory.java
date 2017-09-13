package org.iocaste.kernel.runtime.shell.factories;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.tooldata.RadioGroup;
import org.iocaste.shell.common.tooldata.ToolData;

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
