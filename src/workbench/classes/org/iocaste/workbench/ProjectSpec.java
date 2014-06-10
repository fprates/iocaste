package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.ViewSpecItem;

public class ProjectSpec extends AbstractViewSpec {

    private final void additem(
            ProjectView project, String container, ViewSpecItem item) {
        String name = item.getName();
        
        project.treeitems.put(name, treeitem(container, name));
        for (ViewSpecItem child : item.getItems())
            additem(project, name, child);
    }
    
    @Override
    protected void execute() {
        ProjectView project;
        Context extcontext = getExtendedContext();
        
        form("main");
        navcontrol("main");
        
        dashboard("main", "project");
        dashboarditem("project", "create");
        dashboarditem("project", "components");
        
        standardcontainer("main", "viewtree");
        for (String view : extcontext.views.keySet()) {
            project = extcontext.views.get(view);
            project.treeitems.put(view, treeitem("viewtree", view));
            for (ViewSpecItem item : project.getItems())
                additem(project, view, item);
        }
    }
    
    private final ProjectTreeItem treeitem(String container, String name) {
        String containeritem;
        ProjectTreeItem treeitem;
        
        treeitem = new ProjectTreeItem();
        nodelist(container, name);
        containeritem = name.concat("_viewtreeitem");
        standardcontainer(name, containeritem);
        treeitem.text = name.concat("_text");
        text(containeritem, treeitem.text);
        
        treeitem.link = name.concat("_link");
        link(containeritem, treeitem.link);
        
        treeitem.container = container;
        
        return treeitem;
    }

}
