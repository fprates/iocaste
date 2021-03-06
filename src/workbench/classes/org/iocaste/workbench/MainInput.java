package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class MainInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext = getExtendedContext();
        tilesset("projects", extcontext.projects);
    }

    @Override
    protected void init(PageBuilderContext context) {
        execute(context);
    }

}
