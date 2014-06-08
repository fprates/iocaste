package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dashboard.DashboardComponent;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.StyleSheet;

public class ProjectConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext;
        Container view;
        DashboardComponent dashitem;
        StyleSheet stylesheet;

        extcontext = getExtendedContext();
        
        dashitem = getDashboardItem("project", "create");
        dashitem.setColor("#50ff50");
        dashitem.addText("create");
        dashitem.add("view");
        
        dashitem = getDashboardItem("project", "viewoptions");
        dashitem.setColor("#ff5050");
        dashitem.addText("viewcomponents");
        dashitem.add("form");
        dashitem.add("navcontrol");
        dashitem.add("dataform");
        
        view = getElement("view");
        stylesheet = context.view.styleSheetInstance();
        stylesheet.newElement(".wb_view");
        stylesheet.put(".wb_view", "float", "left");
        view.setStyleClass("wb_view");
        
        view.setVisible(extcontext.viewtree);
    }

}
