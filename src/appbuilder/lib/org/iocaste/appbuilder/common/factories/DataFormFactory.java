package org.iocaste.appbuilder.common.factories;

import org.iocaste.appbuilder.common.ComponentEntry;
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
    public final void generate(ComponentEntry entry) {
        entry.component = new DataFormTool(entry);
    }
}
