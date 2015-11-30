package org.iocaste.appbuilder.common.factories;

import org.iocaste.appbuilder.common.tabletool.AbstractTableHandler;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.appbuilder.common.tabletool.TableToolEntry;
import org.iocaste.shell.common.StandardContainer;

public class TableToolFactory extends AbstractSpecFactory {

    @Override
    protected void execute() {
        TableToolData ttdata;
        
        ttdata = new TableToolData();
        ttdata.context = context;
        ttdata.name = name;
        new StandardContainer(container, ttdata.name);
        components.add(ttdata);
    }

    @Override
    public final void generate() {
        for (TableToolEntry entry : components.tabletools.values())
            entry.component = new TableTool(context, entry.data.name);
    }
    
    @Override
    public final void update() {
        for (TableToolEntry entry : components.tabletools.values())
            if (entry.update)
                AbstractTableHandler.setObject(entry.component, entry.data);
    }
}
