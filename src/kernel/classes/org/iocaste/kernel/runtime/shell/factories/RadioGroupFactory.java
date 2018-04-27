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
        String[] tokens;
        ToolData tooldata = viewctx.entries.get(name).data;
        if (tooldata.value == null)
            return;
        if ((tokens = ((String)tooldata.value).split("\\.")).length == 1)
            return;
        tooldata.value = tokens[1];
    }

}
