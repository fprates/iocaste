package org.iocaste.workbench.project.viewer;

import java.util.Map;

import org.iocaste.appbuilder.common.tabletool.TableToolContextEntry;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.workbench.Context;

public class ViewerItemLoader implements ItemLoader {
    private String table;
    private String item;
    
    public ViewerItemLoader(String table, String item) {
        this.table = table;
        this.item = item;
    }
    
    @Override
    public final void execute(ViewerItemPickData pickdata, Context extcontext) {
        TableToolContextEntry entry;
        Map<Object, ExtendedObject> items;
        ComplexDocument document = (ComplexDocument)extcontext.callreturn;
        
        entry = extcontext.
                tableInstance(pickdata.redirect, table);
        entry.items.clear();
        extcontext.titlearg = extcontext.model.getstKey();
        
        items = document.getItemsMap(item);
        if (items == null)
            return;
        for (Object key : items.keySet())
            extcontext.add(pickdata.redirect, table, items.get(key));
    }
    
    @Override
    public final void init(Context excontext,
            ViewerItemPickData pickdata, Map<String, Object> parameters) {
        parameters.put("name", pickdata.value);
    }
    
}