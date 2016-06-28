package org.iocaste.workbench.project.view.config;

import org.iocaste.appbuilder.common.ViewSpecItem;

public class TableToolConfig extends AbstractViewElementConfig {
    
    public TableToolConfig(ViewConfigContext context) {
        super(context, ViewSpecItem.TYPES.TABLE_TOOL);
        add(new ModelSet());
    }
}
