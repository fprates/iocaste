package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewSpecItem;

public class ProjectSave extends AbstractActionHandler {
    
    private void add(StringBuilder content, StringBuilder level, int type,
            String... args) {

        content.append(level).append(":").append(type);
        if (args != null)
            for (String arg : args)
                content.append(":").append(arg);
        
        content.append("\n");
    }
    
    private void additem(
            StringBuilder content, StringBuilder parent, ProjectTreeItem item) {
        StringBuilder level;
        
        level = marklevel(parent, item.name);
        add(content, level, item.type);
        for (ProjectTreeItem child : item.items)
            additem(content, level, child);
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        StringBuilder content, projectlevel, viewlevel;
        ProjectView project;
        Context extcontext = getExtendedContext();
        
        content = new StringBuilder();
        projectlevel = marklevel(null, extcontext.project);
        add(content, projectlevel, -1);
        
        for (String view : extcontext.views.keySet()) {
            viewlevel = marklevel(projectlevel, view);
            add(content, viewlevel, ViewSpecItem.TYPES.VIEW.ordinal());
            project = extcontext.views.get(view);
            for (ProjectTreeItem item : project.treeitems.get(view).items)
                additem(content, viewlevel, item);
        }
    }
    
    private StringBuilder marklevel(StringBuilder level, String name) {
        StringBuilder result = new StringBuilder();
        
        if (level != null)
            result.append(level).append(".");
        
        return result.append(name);
    }

}
