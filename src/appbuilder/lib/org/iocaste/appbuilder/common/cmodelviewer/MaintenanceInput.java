package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexModel;

public class MaintenanceInput extends AbstractViewInput {
    
    @Override
    protected void execute(PageBuilderContext context) {
        ComplexModel cmodel;
        TableToolContextEntry entry;
        Context extcontext = getExtendedContext();
        
        dfkeyset("head", extcontext.id);
        
        for (String name : extcontext.dataforms.keySet())
            dfset(name, extcontext.dataforms.get(name));
        
        for (String name : extcontext.tabletools.keySet()) {
            entry = extcontext.tabletools.get(name);
            switch (entry.source) {
            case TableToolContextEntry.DOCUMENT:
                if (extcontext.document == null)
                    continue;
                tableitemsset(
                        name, extcontext.document.getItems(entry.cmodelitem));
                break;
            case TableToolContextEntry.BUFFER:
                tableitemsset(name, entry.items.values());
                break;
            }
        }
        
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
    
    @Override
    protected void init(PageBuilderContext context) {
        execute(context);
    }
}
