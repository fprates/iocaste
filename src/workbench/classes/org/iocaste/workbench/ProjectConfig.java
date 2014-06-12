package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.NavControl;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dashboard.DashboardComponent;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.TableTool;

public class ProjectConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        Container container;
        NavControl navcontrol;
        Context extcontext;
        DashboardComponent dashitem;
        StyleSheet stylesheet;
        DataForm head;
        TableTool tabletool;

        extcontext = getExtendedContext();
        
        navcontrol = getNavControl();
        navcontrol.add("save");
        
        head = getElement("head");
        head.importModel("WB_PROJECT", context.function);
        head.get("PROJECT_ID").setEnabled(false);
        
        dashitem = getDashboardItem("project", "attributes");
        dashitem.setColor("#a0a0a0");
        dashitem.addText("attributes");
        dashitem.add("links");
        
        dashitem = getDashboardItem("project", "create");
        dashitem.setColor("#50ff50");
        dashitem.addText("create");
        dashitem.add("view");
        
        dashitem = getDashboardItem("project", "components");
        dashitem.setColor("#ff5050");
        dashitem.addText("viewcomponents");
        dashitem.add("form");
        dashitem.add("navcontrol");
        dashitem.add("dataform");
        dashitem.setVisible(extcontext.viewoptions);
        
        container = getElement("viewtree");
        stylesheet = context.view.styleSheetInstance();
        stylesheet.newElement(".wb_view");
        stylesheet.put(".wb_view", "float", "left");
        container.setStyleClass("wb_view");
        container.setVisible(extcontext.viewtree);
        
        stylesheet.newElement(".wb_text");
        stylesheet.put(".wb_text", "display", "inline");
        
        for (ProjectView project : extcontext.views.values())
            for (ProjectTreeItem item : project.treeitems.values())
                getElement(item.text).setStyleClass("wb_text");
        
        container = getElement("linkscnt");
        container.setVisible(extcontext.links);
        tabletool = getTableTool("links");
        tabletool.model("TASKS");
        tabletool.setMode(TableTool.UPDATE);
    }

}
