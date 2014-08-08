package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.NavControl;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dashboard.DashboardComponent;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.StyleSheet;

public class ProjectConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        ProjectView projectview;
        Container container;
        NavControl navcontrol;
        Context extcontext;
        DashboardComponent dashitem;
        StyleSheet stylesheet;
        DataForm dataform;
        TableToolData tabletool;

        extcontext = getExtendedContext();
        stylesheet = context.view.styleSheetInstance();
        
        navcontrol = getNavControl();
        navcontrol.add("save");
        
        dataform = getElement("head");
        dataform.importModel("WB_PROJECT", context.function);
        dataform.get("PROJECT_ID").setEnabled(false);
        
        dashitem = getDashboardItem("project", "attributes");
        dashitem.setColor("#a0a0a0");
        dashitem.addText("attributes");
        dashitem.add("links");
        
        dashitem = getDashboardItem("project", "create");
        dashitem.setColor("#50ff50");
        dashitem.addText("create");
        dashitem.add("view");
        dashitem.add("model");
        
        dashitem = getDashboardItem("project", "views");
        dashitem.setColor("#a0a0ff");
        dashitem.addText("views");
        if (extcontext.views.size() > 0)
            dashitem.show();
        else
            dashitem.hide();
        
        dashitem = getDashboardItem("project", "components");
        dashitem.setColor("#ff5050");
        dashitem.addText("viewcomponents");
        dashitem.add("form");
        dashitem.add("navcontrol");
        dashitem.add("dataform");
        if (extcontext.viewoptions)
            dashitem.show();
        else
            dashitem.hide();
        
        stylesheet.newElement(".wb_view");
        stylesheet.put(".wb_view", "float", "left");
        container = getElement("viewtree");
        container.setStyleClass("wb_view");
        container.setVisible(extcontext.viewtree);
        stylesheet.newElement(".wb_text");
        stylesheet.put(".wb_text", "display", "inline");
        if (extcontext.view != null) {
            projectview = extcontext.views.get(extcontext.view);
            for (ProjectTreeItem item : projectview.treeitems.values())
                getElement(item.text).setStyleClass("wb_text");
        }
        
        container = getElement("linkscnt");
        container.setVisible(extcontext.linksgrid);
        container.setStyleClass("wb_view");
        tabletool = getTableTool("links");
        tabletool.model = "TASKS";
        tabletool.mode = TableTool.UPDATE;
        tabletool.borderstyle = "overflow: auto;";
        
        container = getElement("modelscnt");
        container.setVisible(extcontext.modelview);
        dataform = getElement("modelhead");
        dataform.importModel("MODEL", context.function);
        container.setStyleClass("wb_view");
        dataform.show("NAME", "TABLE");
        tabletool = getTableTool("modelitems");
        tabletool.model = "MODELITEM";
        tabletool.mode = TableTool.UPDATE;
        tabletool.hide = new String[] {
                "NAME", "MODEL", "INDEX", "ATTRIB", "ITEM_REF"
        };
        tabletool.borderstyle = "overflow: auto; display: inline";
        if (extcontext.modelview){
            navcontrol.add("accept_model");
            navcontrol.add("create_element");
        }
        
        if (extcontext.elementview) {
            navcontrol.add("cancel_element");
            navcontrol.add("accept_element");
        }
        
        container = getElement("elementcnt");
        container.setVisible(extcontext.elementview);
        container.setStyleClass("wb_view");
        dataform = getElement("dataelement");
        dataform.importModel("DATAELEMENT", context.function);
    }

}
