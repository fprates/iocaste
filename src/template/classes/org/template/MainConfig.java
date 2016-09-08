package org.template;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class MainConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        context.view.setTitle("iocaste-template");
    }

}
