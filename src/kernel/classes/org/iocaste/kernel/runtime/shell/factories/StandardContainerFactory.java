package org.iocaste.kernel.runtime.shell.factories;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.tooldata.StandardContainer;

public class StandardContainerFactory extends AbstractSpecFactory {

    @Override
    protected void execute(ViewContext viewctx,
    		Container container, String parent, String name) {
        new StandardContainer(viewctx, name);
    }

}
