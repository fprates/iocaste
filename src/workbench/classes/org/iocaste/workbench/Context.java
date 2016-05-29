package org.iocaste.workbench;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.workbench.project.ProjectAdd;
import org.iocaste.workbench.project.ProjectList;
import org.iocaste.workbench.project.ProjectUse;
import org.iocaste.workbench.project.compile.Compile;

public class Context extends AbstractExtendedContext {
    public Map<String, AbstractCommand> commands;
    public List<String> output;
    public ComplexDocument project;
    
    public Context(PageBuilderContext context) {
        super(context);
        output = new ArrayList<>();
        commands = new HashMap<>();
        commands.put("compile", new Compile());
        commands.put("project-add", new ProjectAdd());
        commands.put("project-list", new ProjectList());
        commands.put("project-use", new ProjectUse());
    }

}
