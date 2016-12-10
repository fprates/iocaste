package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.ContextEntry;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.StandardViewInput;
import org.iocaste.documents.common.ExtendedObject;

public class MaintenanceInput extends StandardViewInput {
    
    @Override
    protected void execute(PageBuilderContext context) {
        ContextEntry ctxentry;
        CModelViewerContext extcontext = getExtendedContext();
        
        super.execute(context);
        if (extcontext.ns != null)
            for (String key : extcontext.models.keySet()) {
                ctxentry = extcontext.tableInstance(key);
                for (ExtendedObject object : ctxentry.getObjects())
                    object.setNS(extcontext.ns);
            }
        loadInputTexts(context);
    }
}
