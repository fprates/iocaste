package org.quantic.template;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class MainConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        // ajusta o título da visão
        context.view.setTitle("iocaste-template");
    }

}
