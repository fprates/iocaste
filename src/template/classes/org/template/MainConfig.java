package org.template;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Text;

public class MainConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        Text text;

        getNavControl().setTitle("iocaste-template");
        
        // inclui um componente de texto na visão
        text = getElement("info");
        text.setText("server.test");
    }

}
