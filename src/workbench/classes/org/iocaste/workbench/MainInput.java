package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class MainInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext = getExtendedContext();
        inputset("command", null);
        if (extcontext.output.size() == 0)
            return;
        for (String line : extcontext.output)
            print(line);
        extcontext.output.clear();
    }

    @Override
    protected void init(PageBuilderContext context) {
        execute(context);
    }

}
