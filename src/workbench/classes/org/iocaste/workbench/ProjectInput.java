package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.Text;

public class ProjectInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        Link link;
        ProjectTreeItem treeitem;
        ProjectView projectview;
        Context extcontext = getExtendedContext();
        
        set("head", "PROJECT_ID", extcontext.project);

        for (String view : extcontext.views.keySet())
            dbitemadd("project", "views", view);
        
        if (extcontext.links != null) {
            tableitemsadd("links", extcontext.links);
            extcontext.links = null;
        }
        
        if (extcontext.view != null) {
            projectview = extcontext.views.get(extcontext.view);
            for (String name : projectview.treeitems.keySet()) {
                treeitem = projectview.treeitems.get(name);
                ((Text)getElement(treeitem.text)).setText(name);
                
                link = getElement(treeitem.link);
                link.setText("+");
                link.setAction("add");
                link.add("name", name);
                link.add("viewname", extcontext.view);
                link.add("container", treeitem.container);
            }
        }
    }
    @Override
    protected void init(PageBuilderContext context) {
        execute(context);
    }

}
