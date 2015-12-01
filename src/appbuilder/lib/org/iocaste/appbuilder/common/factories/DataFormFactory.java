package org.iocaste.appbuilder.common.factories;

import java.util.Map;

import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.appbuilder.common.dataformtool.DataFormTool;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.shell.common.StandardContainer;

public class DataFormFactory extends AbstractSpecFactory {
    
    @Override
    protected void execute() {
        DataFormToolData dfdata;
        
        dfdata = new DataFormToolData();
        dfdata.context = context;
        dfdata.name = name;
        
        new StandardContainer(container, dfdata.name);
        components.add(dfdata);
    }
    
    @Override
    public final void generate() {
        Map<String, ComponentEntry> subentries;
        
        subentries = components.entries.get(ViewSpecItem.TYPES.DATA_FORM);
        if (subentries == null)
            return;
        for (ComponentEntry entry : subentries.values()) {
            entry.component = new DataFormTool(entry);
            entry.component.run();
        }
    }
    
    @Override
    public final void update() {
        Map<String, ComponentEntry> subentries;
        
        subentries = components.entries.get(ViewSpecItem.TYPES.DATA_FORM);
        if (subentries == null)
            return;
        for (ComponentEntry entry : subentries.values())
            if (entry.update)
                entry.component.refresh();
    }
}
