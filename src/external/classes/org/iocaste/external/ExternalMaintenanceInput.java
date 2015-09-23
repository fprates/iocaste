package org.iocaste.external;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.cmodelviewer.MaintenanceInput;
import org.iocaste.documents.common.ExtendedObject;

public class ExternalMaintenanceInput extends MaintenanceInput {

    @Override
    public final void execute(PageBuilderContext context) {
        ExternalContext extcontext;
        
        extcontext = getExtendedContext();
        if (extcontext.modelitems.size() > 0) {
            if (extcontext.document == null) {
                tableitemsset("items_table", extcontext.modelitems);
            } else {
                extcontext.document.remove("items");
                extcontext.document.add(
                        extcontext.modelitems.toArray(new ExtendedObject[0]));
            }
            
            extcontext.modelitems.clear();
        }
        
        super.execute(context);
    }
}
