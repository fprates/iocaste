package org.iocaste.workbench.project.java;

import java.util.Map;

import org.iocaste.appbuilder.common.cmodelviewer.TableToolContextEntry;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.viewer.ItemLoader;
import org.iocaste.workbench.project.viewer.ViewerItemPickData;

public class PackageItemLoader implements ItemLoader {
    
    @Override
    public final void execute(ViewerItemPickData pickdata, Context extcontext) {
        TableToolContextEntry entry;
        Map<Object, ExtendedObject> items;
        
        entry = extcontext.
                tableInstance(pickdata.redirect, "package_item_items");
        entry.items.clear();
        extcontext.pkgitem = extcontext.project.getDocumentsMap("class").
                get(pickdata.value);
        items = extcontext.pkgitem.getItemsMap("class");
        if ((items == null) || items.size() == 0)
            return;
        for (Object key : items.keySet())
            extcontext.add(
                    pickdata.redirect, "package_item_items", items.get(key));
    }

    @Override
    public void init(Context excontext,
            ViewerItemPickData pickdata, Map<String, Object> parameters) {
        parameters.put("name", pickdata.value);
    }
    
}