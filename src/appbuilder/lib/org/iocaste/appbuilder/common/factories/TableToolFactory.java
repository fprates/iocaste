package org.iocaste.appbuilder.common.factories;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;

public class TableToolFactory extends AbstractSpecFactory {

    @Override
    protected AbstractComponentData dataInstance() {
        return new TableToolData();
    }

    @Override
    public final void generate(ComponentEntry entry) {
        entry.component = new TableTool(entry);
    }
}
