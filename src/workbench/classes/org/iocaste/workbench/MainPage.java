package org.iocaste.workbench;

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
