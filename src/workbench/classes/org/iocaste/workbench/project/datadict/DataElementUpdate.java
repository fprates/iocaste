package org.iocaste.workbench.project.datadict;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.viewer.ViewerUpdate;

class DataElementUpdate implements ViewerUpdate {

    @Override
    public void execute(Context extcontext, Object object) {
        extcontext.add("data_elements_items", (ExtendedObject)object);
    }
    
}