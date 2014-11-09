package org.iocaste.appbuilder;

import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.View;

public class TableToolRenderer extends AbstractFunction {
    
    public TableToolRenderer() {
        export("add_action", new AddItem());
        export("items_add", new AddItems());
        export("multiple_objects_set", new SetMultipleObjects());
        export("objects_set", new SetObjects());
        export("render", new TableRender());
    }
}

class Context {
    public TableToolData data;
    public Table table;
    public Button accept, add, remove;
    public View view;
}