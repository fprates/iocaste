package org.iocaste.appbuilder.common.factories;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.appbuilder.common.dataformtool.DataFormTool;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;

public class DataFormFactory extends AbstractSpecFactory {
    
    @Override
    protected AbstractComponentData dataInstance() {
        return new DataFormToolData();
    }
    
    @Override
    public final void generate(ComponentEntry entry) {
        entry.component = new DataFormTool(entry);
    }
}
