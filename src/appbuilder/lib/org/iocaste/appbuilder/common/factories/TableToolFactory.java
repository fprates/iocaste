package org.iocaste.appbuilder.common.factories;

import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.appbuilder.common.ViewComponents;
import org.iocaste.appbuilder.common.ViewSpecItem;
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
    public final void generate(ViewComponents components) {
        for (ComponentEntry entry : components.entries.values())
            if (entry.data.type.equals(ViewSpecItem.TYPES.TABLE_TOOL))
                entry.component = new TableTool(entry);
    }
    
    @Override
    public final void update(ViewComponents components) {
        for (ComponentEntry entry : components.entries.values())
            if (entry.update &&
                    entry.data.type.equals(ViewSpecItem.TYPES.TABLE_TOOL))
                entry.component.refresh();
    }
}
