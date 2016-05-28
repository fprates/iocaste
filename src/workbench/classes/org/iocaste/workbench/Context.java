package org.iocaste.workbench;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.workbench.project.ProjectAdd;
import org.iocaste.workbench.project.compile.Compile;

public class Context extends AbstractExtendedContext {
    public Map<String, AbstractCommand> commands;
    
    public Context(PageBuilderContext context) {
        super(context);
        commands = new HashMap<>();
        commands.put("compile", new Compile());
        commands.put("project-add", new ProjectAdd());
    }

}
