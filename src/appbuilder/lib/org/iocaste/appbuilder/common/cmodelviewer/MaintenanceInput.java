package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexModel;

public class MaintenanceInput extends AbstractViewInput {
    
    @Override
    protected void execute(PageBuilderContext context) {
        ComplexModel cmodel;
        Context extcontext = getExtendedContext();
        
        dfkeyset("head", extcontext.id);
        
        for (String name : extcontext.dataforms.keySet())
            dfset(name, extcontext.dataforms.get(name));
        
        for (String name : extcontext.tabletools.keySet())
            tableitemsset(name, extcontext.tabletools.get(name).values());
        
        if (extcontext.document != null) {
            cmodel = extcontext.document.getModel();
            if (cmodel.getHeader().getNamespace() != null) {
                dfnsset("head", extcontext.ns);
                dfnsset("base", extcontext.ns);
            }
            
            dfset("base", extcontext.document.getHeader());
            for (String name : cmodel.getItems().keySet())
                tableitemsset(name.concat("_table"), extcontext.document.
                        getItems(name));
        }
        
        loadInputTexts(context);
    }
    
    @Override
    protected void init(PageBuilderContext context) {
        execute(context);
    }
}
