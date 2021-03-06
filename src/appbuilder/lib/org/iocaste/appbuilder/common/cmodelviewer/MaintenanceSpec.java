package org.iocaste.appbuilder.common.cmodelviewer;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.ComplexModelItem;
import org.iocaste.documents.common.Documents;

public class MaintenanceSpec extends AbstractViewSpec {
    
    @Override
    public void execute(PageBuilderContext context) {
        CModelViewerContext extcontext = getExtendedContext();
        ComplexModel model = new Documents(context.function).
                getComplexModel(extcontext.link.cmodel);
        
        dataform(parent, "head");
        tabbedpane(parent, "tabs");
        tabbedpaneitem("tabs", "basetab");
        dataform("basetab", "base");
        tabs(extcontext, model.getItems());
    }

    protected void tabs(CModelViewerContext extcontext,
            Map<String, ComplexModelItem> models) {
        String tablename;
        
        for (String name : models.keySet()) {
            if (models.get(name).model == null)
                continue;
            tabbedpaneitem("tabs", name);
            tablename = name.concat("_table");
            tabletool(name, tablename);
            extcontext.models.put(tablename, name);
        }
    }
}
