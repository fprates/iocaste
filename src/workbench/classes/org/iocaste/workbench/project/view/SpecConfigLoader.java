package org.iocaste.workbench.project.view;

import java.util.Map;

import org.iocaste.appbuilder.common.cmodelviewer.TableToolContextEntry;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.viewer.ItemLoader;
import org.iocaste.workbench.project.viewer.ViewerItemPickData;

public class SpecConfigLoader implements ItemLoader {

    @Override
    public final void execute(ViewerItemPickData pickdata, Context extcontext) {
        TableToolContextEntry entry;
        Map<Object, ExtendedObject> items;
        
        extcontext.spec = extcontext.view.getDocumentsMap("spec").
                get(pickdata.value);
        entry = extcontext.tableInstance(
                "spec_config_editor", "spec_config_items");
        entry.items.clear();
        extcontext.titlearg = pickdata.value;
        
        items = extcontext.spec.getItemsMap("config");
        if (items == null)
            return;
        for (Object key : items.keySet())
            extcontext.add(
                    pickdata.redirect, "spec_config_items", items.get(key));
    }

    @Override
    public void init(Context excontext,
            ViewerItemPickData pickdata, Map<String, Object> parameters) {
        parameters.put("name", pickdata.value);
    }
    
}