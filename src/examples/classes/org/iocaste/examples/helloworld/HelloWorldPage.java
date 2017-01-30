package org.iocaste.examples.helloworld;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

public class HelloWorldPage extends AbstractPanelPage {

    @Override
    public void execute() throws Exception {
        set(new HelloWorldSpec());
        set(new HelloWorldConfig());
        set(new HelloWorldStyle());
    }
    
}
