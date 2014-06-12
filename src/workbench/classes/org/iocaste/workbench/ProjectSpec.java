package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.ViewSpecItem;

public class ProjectSpec extends AbstractViewSpec {

    private final ProjectTreeItem additem(
            ProjectView project, String container, ViewSpecItem item) {
        ProjectTreeItem treeitem;
        String name = item.getName();
        
        treeitem = treeitem(container, name, item.getType());
        project.treeitems.put(name, treeitem);
        
        for (ViewSpecItem child : item.getItems())
            treeitem.items.add(additem(project, name, child));
        
        return treeitem;
    }
    
    @Override
    protected void execute() {
        ProjectTreeItem treeitem;
        ProjectView projectview;
        Context extcontext = getExtendedContext();
        
        form("main");
        navcontrol("main");
        dataform("main", "head");
        
        dashboard("main", "project");
        dashboarditem("project", "attributes");
        dashboarditem("project", "create");
        dashboarditem("project", "views");
        dashboarditem("project", "components");
        
        standardcontainer("main", "viewtree");
        
        if (extcontext.view != null) {
            projectview = extcontext.views.get(extcontext.view);
            treeitem = treeitem("viewtree", extcontext.view,
                    ViewSpecItem.TYPES.VIEW.ordinal());
            projectview.treeitems.put(extcontext.view, treeitem);
            for (ViewSpecItem item : projectview.getItems())
                treeitem.items.add(additem(projectview, extcontext.view, item));
        }
        
        standardcontainer("main", "linkscnt");
        tabletool("linkscnt", "links");
    }
    
    private final ProjectTreeItem treeitem(
            String container, String name, int type) {
        String containeritem;
        ProjectTreeItem treeitem;
        
        treeitem = new ProjectTreeItem();
        nodelist(container, name);
        containeritem = name.concat("_viewtreeitem");
        standardcontainer(name, containeritem);
        
        treeitem.name = name;
        treeitem.text = name.concat("_text");
        treeitem.link = name.concat("_link");
        treeitem.container = container;
        treeitem.type = type;
        
        text(containeritem, treeitem.text);
        link(containeritem, treeitem.link);
        return treeitem;
    }

}
