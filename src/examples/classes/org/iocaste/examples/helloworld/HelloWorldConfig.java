package org.iocaste.examples.helloworld;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class HelloWorldConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        getNavControl().setTitle("hello-world");
        getElement("hello").setStyleClass("hello_text");
    }
    
}