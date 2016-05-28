package org.iocaste.workbench;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

public class MainPage extends AbstractPanelPage {

    @Override
    public void execute() {
        Context extcontext = getExtendedContext();
        
        set(new MainSpec());
        set(new MainConfig());
        set(new MainInput());
        submit("execute", new ExecuteCommand());
        for (String key : extcontext.commands.keySet())
            put(key, extcontext.commands.get(key));
    }
    
}