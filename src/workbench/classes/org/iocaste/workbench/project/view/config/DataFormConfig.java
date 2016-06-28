package org.iocaste.workbench.project.view.config;

import org.iocaste.appbuilder.common.ViewSpecItem;

public class DataFormConfig extends AbstractViewElementConfig {
    
    public DataFormConfig(ViewConfigContext context) {
        super(context, ViewSpecItem.TYPES.DATA_FORM);
        add(new ModelSet());
    }
}
