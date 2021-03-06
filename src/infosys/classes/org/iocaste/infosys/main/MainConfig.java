package org.iocaste.infosys.main;

import org.iocaste.infosys.InfosysContext;
import org.iocaste.runtime.common.page.AbstractViewConfig;
import org.iocaste.shell.common.tooldata.ToolData;

public class MainConfig extends AbstractViewConfig<InfosysContext> {

    @Override
    protected void execute(InfosysContext context) {
        ToolData tool = getTool("connections");
        
        tool.model = "IS_CONNECTION";
    }
    
}