package org.iocaste.appbuilder.common.cmodelviewer;

import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.panel.AbstractPanelSpec;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;

public class MaintenanceSpec extends AbstractPanelSpec {
    
    @Override
    public void execute(PageBuilderContext context) {
        Context extcontext = getExtendedContext();
        ComplexModel model = getManager(extcontext.link.cmodel).getModel();
        
        extcontext.pageInstance();
        
        dataform("content", "head");
        skip("content");
        tabbedpane("content", "tabs");
        tabbedpaneitem("tabs", "basetab");
        dataform("basetab", "base");
        tabs(extcontext, model.getItems());
    }

    protected void tabs(Context extcontext, Map<String, DocumentModel> models) {
        String tablename;
        
        for (String name : models.keySet()) {
            tabbedpaneitem("tabs", name);
            tablename = name.concat("_table");
            tabletool(name, tablename);
            extcontext.tableInstance(tablename).cmodelitem = name;
        }
    }
}
