package org.iocaste.external;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.cmodelviewer.MaintenanceConfig;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.shell.common.NodeList;

public class ExternalMaintenanceConfig extends MaintenanceConfig {

    @Override
    public final void execute(PageBuilderContext context) {
        DataFormToolData form;
        NodeList node;
        
        super.execute(context);
        
        form = getTool("importobj");
        form.model = "XTRNL_IMPORT_OBJECT";
        form.style = "xtrnl_import_form";
        form.instance("NAME").label = "modelname";
        
        node = getElement("import");
        node.setStyleClass("xtrnl_import");
        node.setItemsStyle("xtrnl_import_item");
    }
}
