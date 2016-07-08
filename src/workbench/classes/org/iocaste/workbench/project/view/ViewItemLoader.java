package org.iocaste.workbench.project.view;

import java.util.Map;

import org.iocaste.appbuilder.common.cmodelviewer.TableToolContextEntry;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.viewer.ItemLoader;
import org.iocaste.workbench.project.viewer.ViewerItemPickData;

public class ViewItemLoader implements ItemLoader {
    
    @Override
    public final void execute(ViewerItemPickData pickdata, Context extcontext) {
        ComplexDocument document = (ComplexDocument)extcontext.callreturn;
        
        loaddoc(document, extcontext, pickdata, "view_item_items", "spec");
        loaditem(document, extcontext, pickdata, "actions_items", "action");
    }
    
    @Override
    public final void init(Context excontext,
            ViewerItemPickData pickdata, Map<String, Object> parameters) {
        parameters.put("name", pickdata.value);
    }
    
    private final void loaddoc(ComplexDocument document, Context extcontext,
            ViewerItemPickData pickdata, String table, String item) {
        TableToolContextEntry entry;
        Map<Object, ComplexDocument> documents;
        
        entry = extcontext.tableInstance(pickdata.redirect, table);
        entry.items.clear();
        extcontext.titlearg = extcontext.view.getstKey();
        
        documents = document.getDocumentsMap(item);
        if (documents == null)
            return;
        for (Object key : documents.keySet())
            extcontext.add(
                    pickdata.redirect, table, documents.get(key).getHeader());
    }

    private final void loaditem(ComplexDocument document, Context extcontext,
            ViewerItemPickData pickdata, String table, String item) {
        TableToolContextEntry entry;
        Map<Object, ExtendedObject> items;
        
        entry = extcontext.tableInstance(pickdata.redirect, table);
        entry.items.clear();
        extcontext.titlearg = extcontext.view.getstKey();
        
        items = document.getItemsMap(item);
        if (items == null)
            return;
        for (Object key : items.keySet())
            extcontext.add(pickdata.redirect, table, items.get(key));
    }

}