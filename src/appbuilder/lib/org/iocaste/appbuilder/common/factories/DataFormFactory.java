package org.iocaste.appbuilder.common.factories;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.appbuilder.common.dataformtool.DataFormSpecItemHandler;
import org.iocaste.appbuilder.common.dataformtool.DataFormTool;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.StandardContainer;

public class DataFormFactory extends AbstractSpecFactory {
    
    public DataFormFactory() {
        setHandler(new DataFormSpecItemHandler());
    }
    
    @Override
    protected final void containerInstance(
            Container container, AbstractComponentData data) {
        data.parent = data.name.concat("_dataform");
        new StandardContainer(container, data.parent);
    }
    
    @Override
    protected AbstractComponentData dataInstance() {
        return new DataFormToolData();
    }
    
    @Override
    public final void generate(ComponentEntry entry) {
        entry.component = new DataFormTool(entry);
    }
}
