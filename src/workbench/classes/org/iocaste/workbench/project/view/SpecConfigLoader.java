package org.iocaste.workbench.project.view;

import java.util.Map;

import org.iocaste.appbuilder.common.cmodelviewer.TableToolContextEntry;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.viewer.ViewerItemLoader;
import org.iocaste.workbench.project.viewer.ViewerItemPickData;

public class SpecConfigLoader implements ViewerItemLoader {

    @Override
    public final void execute(ViewerItemPickData pickdata, Context extcontext) {
        TableToolContextEntry entry;
        Map<Object, ExtendedObject> items;

        extcontext.specitem = extcontext.view.getItemsMap("spec").
                get(pickdata.value);
        entry = extcontext.tableInstance(
                "spec_config_editor", "spec_config_items");
        entry.items.clear();
        items = extcontext.view.getItemsMap("config");
        if ((items == null) || (items.size() == 0))
            return;
        for (Object key : items.keySet())
            extcontext.add(
                    "spec_config_editor", "spec_config_items", items.get(key));
    }
    
}