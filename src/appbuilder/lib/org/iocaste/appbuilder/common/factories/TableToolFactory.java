package org.iocaste.appbuilder.common.factories;

import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.StandardContainer;

public class TableToolFactory extends AbstractSpecFactory {

    @Override
    protected void execute(Container container, String parent, String name) {
        TableToolData ttdata;
        
        ttdata = new TableToolData();
        ttdata.context = context;
        ttdata.name = name;
        new StandardContainer(container, ttdata.name);
        components.add(ttdata);
    }

    @Override
    public final void generate(ComponentEntry entry) {
        entry.component = new TableTool(entry);
    }
}
