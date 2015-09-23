package org.iocaste.packagetool;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class MainInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext;
        
        extcontext = getExtendedContext();
        tableitemsset("inpackages", extcontext.installed);
        tableitemsset("unpackages", extcontext.uninstalled);
        tableitemsset("erpackages", extcontext.invalid);
    }

    @Override
    protected void init(PageBuilderContext context) {
        execute(context);
    }

}
