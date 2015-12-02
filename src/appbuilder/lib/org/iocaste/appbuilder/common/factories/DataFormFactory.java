package org.iocaste.appbuilder.common.factories;

import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.appbuilder.common.ViewComponents;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.appbuilder.common.dataformtool.DataFormTool;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.StandardContainer;

public class DataFormFactory extends AbstractSpecFactory {
    
    @Override
    protected void execute(Container container, String parent, String name) {
        DataFormToolData dfdata;
        
        dfdata = new DataFormToolData();
        dfdata.context = context;
        dfdata.name = name;
        
        new StandardContainer(container, dfdata.name);
        components.add(dfdata);
    }
    
    @Override
    public final void generate(ViewComponents components) {
        for (ComponentEntry entry : components.entries.values()) {
            if (!entry.data.type.equals(ViewSpecItem.TYPES.DATA_FORM))
                continue;
            entry.component = new DataFormTool(entry);
            entry.component.run();
            entry.component.refresh();
        }
    }
    
    @Override
    public final void update(ViewComponents components) {
        for (ComponentEntry entry : components.entries.values()) {
            if (!entry.data.type.equals(ViewSpecItem.TYPES.DATA_FORM))
                continue;
            if (entry.update)
                entry.component.refresh();
        }
    }
}
