package org.iocaste.workbench.project.java.editor;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolItem;

public class ClassEditorConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        DataFormToolData dataform;
        DataFormToolItem item;
        
        context.view.setFocus(getElement("source"));
        
        dataform = getTool("head");
        dataform.model = "WB_JAVA_CLASS";
        dataform.show = new String[] {"PACKAGE", "NAME"};
        item = dataform.itemInstance("PACKAGE");
        item.vlength = 80;
        item.disabled = true;
        item = dataform.itemInstance("NAME");
        item.vlength = 80;
        item.disabled = true;
    }
    
}
