package org.iocaste.setup;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;

public class Main extends AbstractPageBuilder {

    @Override
    public void config(PageBuilderContext context) throws Exception {
        
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        installObject("user", new User());
        installObject("cmodel", new CModel());
        installObject("gconfig", new GlobalConfig());
        installObject("shell", new Shell());
        installObject("main", new Install());
    }
}