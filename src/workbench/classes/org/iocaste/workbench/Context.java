package org.iocaste.workbench;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.workbench.project.ProjectAdd;

public class Context extends AbstractExtendedContext {
    public Map<String, AbstractCommand> commands;
    
    public Context(PageBuilderContext context) {
        super(context);
        commands = new HashMap<>();
        commands.put("project-add", new ProjectAdd());
    }

}
