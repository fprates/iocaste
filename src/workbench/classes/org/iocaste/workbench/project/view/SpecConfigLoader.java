package org.iocaste.workbench.project.view;

import java.util.Map;

import org.iocaste.appbuilder.common.tabletool.TableToolContextEntry;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.viewer.ItemLoader;
import org.iocaste.workbench.project.viewer.ViewerItemPickData;

public class SpecConfigLoader implements ItemLoader {

    @Override
    public final void execute(ViewerItemPickData pickdata, Context extcontext) {
        
        extcontext.spec = extcontext.view.getDocumentsMap("spec").
                get(pickdata.value);
        extcontext.titlearg = pickdata.value;
        load(extcontext, pickdata, "config", "spec_config_items");
        load(extcontext, pickdata, "tool_item", "tool_item_items");
    }

    @Override
    public void init(Context excontext,
            ViewerItemPickData pickdata, Map<String, Object> parameters) {
        parameters.put("name", pickdata.value);
    }
    
    private void load(Context extcontext, ViewerItemPickData pickdata,
            String view, String itemsname) {
        TableToolContextEntry entry;
        Map<Object, ExtendedObject> items;
        
        entry = extcontext.tableInstance("spec_config_editor", itemsname);
        entry.items.clear();
        
        items = extcontext.spec.getItemsMap(view);
        if (items == null)
            return;
        for (Object key : items.keySet())
            extcontext.add(pickdata.redirect, itemsname, items.get(key));
    }
    
}