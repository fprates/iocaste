package org.iocaste.external;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.cmodelviewer.Context;
import org.iocaste.appbuilder.common.cmodelviewer.MaintenanceSpec;
import org.iocaste.documents.common.ComplexModel;

public class ExternalMaintenanceSpec extends MaintenanceSpec {

    @Override
    public final void execute(PageBuilderContext context) {
        Context extcontext = getExtendedContext();
        ComplexModel model = getManager(extcontext.link.cmodel).getModel();
        
        dataform("content", "head");
        skip("content");
        tabbedpane("content", "tabs");
        tabbedpaneitem("tabs", "basetab");
        dataform("basetab", "base");
        for (String name : model.getItems().keySet()) {
            tabbedpaneitem("tabs", name);
            if (name.equals("items")) {
                nodelist(name, "import");
                dataform("import", "importobj");
                button("import", "importmodel");
            }
            
            tabletool(name, name.concat("_table"));
        }
    }
}
