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
        ExtendedObject object;
        String spec;
        
        extcontext.specitem = extcontext.view.getItemsMap("spec").
                get(pickdata.value);
        entry = extcontext.tableInstance(
                "spec_config_editor", "spec_config_items");
        entry.items.clear();
        items = extcontext.view.getItemsMap("config");
        if ((items == null) || (items.size() == 0))
            return;
        spec = extcontext.specitem.getst("ITEM_ID");
        for (Object key : items.keySet()) {
            object = items.get(key);
            if (!object.getst("SPEC").equals(spec))
                continue;
            extcontext.add("spec_config_editor", "spec_config_items", object);
        }
        extcontext.titlearg = pickdata.value;
    }

    @Override
    public void init(Context excontext,
            ViewerItemPickData pickdata, Map<String, Object> parameters) {
        parameters.put("name", pickdata.value);
    }
    
}