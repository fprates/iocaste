package org.iocaste.workbench.project.view;

import java.util.Map;

import org.iocaste.appbuilder.common.cmodelviewer.TableToolContextEntry;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.viewer.ViewerItemLoader;

public class ViewItemLoader implements ViewerItemLoader {

    @Override
    public final void execute(Context extcontext) {
        TableToolContextEntry entry;
        ComplexDocument document = (ComplexDocument)extcontext.callreturn;
        Map<Object, ExtendedObject> items = document.getItemsMap("spec");
        
        entry = extcontext.tableInstance("view_item_editor", "view_item_items");
        entry.items.clear();
        for (Object key : items.keySet())
            extcontext.add(
                    "view_item_editor", "view_item_items", items.get(key));
    }
    
}