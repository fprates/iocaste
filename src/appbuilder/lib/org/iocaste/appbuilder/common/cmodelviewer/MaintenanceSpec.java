package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.panel.AbstractPanelSpec;
import org.iocaste.documents.common.ComplexModel;

public class MaintenanceSpec extends AbstractPanelSpec {
    
    @Override
    public void execute(PageBuilderContext context) {
        Context extcontext = getExtendedContext();
        ComplexModel model = getManager(extcontext.link.cmodel).getModel();
        
        dataform("content", "head");
        skip("content");
        tabbedpane("content", "tabs");
        tabbedpaneitem("tabs", "basetab");
        dataform("basetab", "base");
        for (String name : model.getItems().keySet()) {
            tabbedpaneitem("tabs", name);
            tabletool(name, name.concat("_table"));
        }
    }

}
