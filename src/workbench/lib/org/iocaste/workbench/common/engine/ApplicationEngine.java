package org.iocaste.workbench.common.engine;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;

public class ApplicationEngine extends AbstractPageBuilder {

    @Override
    public void config(PageBuilderContext context) { }
    
    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        byte[] buffer = getApplicationContext(
                getRealPath("META-INF", "context.txt"));
        
        installObject("auto", new AutomatedInstall(buffer));
    }

}
