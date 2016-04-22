package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.StandardViewInput;
import org.iocaste.documents.common.ComplexModel;

public class MaintenanceInput extends StandardViewInput {
    
    @Override
    protected void execute(PageBuilderContext context) {
        ComplexModel cmodel;
        Context extcontext = getExtendedContext();
        
        dfkeyset("head", extcontext.id);
        
        super.execute(context);
        
        if (extcontext.document != null) {
            cmodel = extcontext.document.getModel();
            if (cmodel.getHeader().getNamespace() != null) {
                dfnsset("head", extcontext.ns);
                dfnsset("base", extcontext.ns);
            }
            
            dfset("base", extcontext.document.getHeader());
        }
        
        loadInputTexts(context);
    }
}
