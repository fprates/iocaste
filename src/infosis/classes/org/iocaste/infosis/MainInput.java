package org.iocaste.infosis;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class MainInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        for (String name : Main.ACTIONS)
            dbitemadd("menu", name, name);
    }

    @Override
    protected void init(PageBuilderContext context) {
        execute(context);
    }

}
