package org.iocaste.workbench.project.datadict;

import java.util.Map;

import org.iocaste.appbuilder.common.cmodelviewer.TableToolContextEntry;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.viewer.ViewerItemLoader;
import org.iocaste.workbench.project.viewer.ViewerItemPickData;

public class ModelItemLoader implements ViewerItemLoader {

    @Override
    public final void execute(ViewerItemPickData pickdata, Context extcontext) {
        TableToolContextEntry entry;
        ComplexDocument document = (ComplexDocument)extcontext.callreturn;
        Map<Object, ExtendedObject> items = document.getItemsMap("item");
        
        entry = extcontext.
                tableInstance("model_item_editor", "model_item_items");
        entry.items.clear();
        for (Object key : items.keySet())
            extcontext.add(
                    "model_item_editor", "model_item_items", items.get(key));
    }
    
}