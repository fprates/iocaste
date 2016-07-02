package org.iocaste.workbench;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.workbench.project.Load;

public class MainPage extends AbstractPanelPage {

    @Override
    public void execute() throws Exception {
        Context extcontext = getExtendedContext();
        PageBuilderContext context = extcontext.getContext();
        
        set(new MainSpec());
        set(new MainConfig());
        set(new MainInput());
        set(new MainStyle());
        put("load", new Load());
        put("project", new ProjectSelect());
        submit("execute", new ExecuteCommand());
        for (String key : extcontext.commands.keySet())
            put(key, extcontext.commands.get(key));
        
        context.getView().run("load", context);
    }
}

class ProjectSelect extends AbstractActionHandler {
    private Map<String, String> parameters;
    
    public ProjectSelect() {
        parameters = new HashMap<>();
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        AbstractCommand command;
        Context extcontext = getExtendedContext();
        String project = getinputst("item_PROJECT");
        
        if (project.equals("project_add")) {
            init("project-add-gui", extcontext);
            redirect("project-add-gui");
            return;
        }
        
        parameters.clear();
        parameters.put("name", project);
        command = context.getView().getActionHandler("project-use");
        command.set(parameters);
        command.run(context);
        init("project-view", extcontext);
        redirect("project-view");
    }
    
}