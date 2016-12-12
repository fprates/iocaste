package org.iocaste.install.finish;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Link;

public class FinishConfig extends AbstractViewConfig {
    
    @Override
    protected void execute(PageBuilderContext context) {
        Link link;
        
        context.view.setTitle("congratulations");
        link = getElement("continue");
        link.setAbsolute(true);
        link.setAction("/iocaste-shell");
    }
    
}
