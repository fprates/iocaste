package org.iocaste.workbench.project.viewer;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.AbstractComponentDataItem;
import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.Context;

public class ProjectViewerConfig extends AbstractViewConfig {
    
    private final void itemconfig(String name, Object[] dataconfig) {
        Object[][] items;
        AbstractComponentData data;
        AbstractComponentDataItem item;
        TableToolData ttdata;
        
        data = getTool(name);
        if (data == null)
            return;
        data.model = (String)dataconfig[1];
        ttdata = null;
        switch ((String)dataconfig[0]) {
        case "tt":
            ttdata = (TableToolData)data;
            ttdata.mark = true;
            ttdata.style = "wb_tt_viewer";
            break;
        case "dt":
            getElement(name).setStyleClass("wb_dt_viewer");
            break;
        }
        
        items = (Object[][])dataconfig[2];
        if (items == null)
            return;
        for (int j = 0; j < items.length; j++) {
            item = data.instance((String)items[j][0]);
            item.invisible = (boolean)items[j][1];
            item.vlength = (int)items[j][4];
            item.disabled = (boolean)items[j][5];
            if (items[j][2] != null) {
                item.componenttype = (Const)items[j][2];
                item.action = (String)items[j][3];
            }
        }
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext = getExtendedContext();
        
        getNavControl().setTitle("viewer.title", extcontext.titlearg);
        for (String name : ViewerLayoutConfig.config.keySet())
            itemconfig(name, ViewerLayoutConfig.config.get(name));
    }
}

