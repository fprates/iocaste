package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class SwitchPanel extends AbstractActionHandler {
    private String panel;
    
    public SwitchPanel(String panel) {
        this.panel = panel;
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Context extcontext = getExtendedContext();
        
        extcontext.hideAll();
        switch (panel) {
        case "element":
            extcontext.elementview = true;
            break;
        case "model":
            extcontext.modelview = true;
            break;
        }
    }

}
