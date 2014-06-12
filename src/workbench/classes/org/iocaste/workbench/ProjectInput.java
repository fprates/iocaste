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
        ProjectView project;
        Context extcontext = getExtendedContext();
        
        set("head", "PROJECT_ID", extcontext.project);
        
        for (String view : extcontext.views.keySet()) {
            project = extcontext.views.get(view);
            for (String name : project.treeitems.keySet()) {
                treeitem = project.treeitems.get(name);
                ((Text)getElement(treeitem.text)).setText(name);
                
                link = getElement(treeitem.link);
                link.setText("+");
                link.setAction("add");
                link.add("name", name);
                link.add("viewname", view);
                link.add("container", treeitem.container);
            }
        }
    }

}
