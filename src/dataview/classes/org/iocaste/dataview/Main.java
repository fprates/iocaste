package org.iocaste.dataview;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.panel.StandardPanel;

public class Main extends AbstractPageBuilder {

    @Override
    public void config(PageBuilderContext context) throws Exception {
        StandardPanel panel;
        Context extcontext;
        
        extcontext = new Context(context);
        
        panel = new StandardPanel(context);
        panel.instance("main", new MainPage(), extcontext);
        panel.instance("output", new OutputPage(), extcontext);
        panel.instance("nsinput", new NSInputPage(), extcontext);
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        
        defaultinstall.setLink("SE16", "iocaste-dataview");
        defaultinstall.addToTaskGroup("DEVELOP", "SE16");
        defaultinstall.setProfile("DEVELOP");
        defaultinstall.setProgramAuthorization("DATAVIEWER");
        
        installObject("main", new Install());
    }
}

class MainPage extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new MainSpec());
        set(new MainConfig());
        submit("select", new Select());
    }
    
}

class OutputPage extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new OutputSpec());
        set(new OutputConfig());
        set(new OutputInput());
    }
    
}

class NSInputPage extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new NSInputSpec());
        set(new NSInputConfig());
        submit("continuesel", new ContinueSelect());
    }
    
}