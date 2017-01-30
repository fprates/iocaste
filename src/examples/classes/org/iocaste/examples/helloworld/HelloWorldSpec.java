package org.iocaste.examples.helloworld;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class HelloWorldSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        text(parent, "hello");
    }
    
}