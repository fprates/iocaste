package org.iocaste.dataview;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.dataview.main.MainPage;
import org.iocaste.dataview.ns.NSInputPage;
import org.iocaste.dataview.output.OutputPage;

public class Main extends AbstractPageBuilder {

    @Override
    public void config(PageBuilderContext context) throws Exception {
        Context extcontext;
        extcontext = new Context(context);
        context.messages = new Messages();
        context.add("main", new MainPage(), extcontext);
        context.add("output", new OutputPage(), extcontext);
        context.add("nsinput", new NSInputPage(), extcontext);
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        
        defaultinstall.setLink("SE16", "iocaste-dataview");
        defaultinstall.addToTaskGroup("DEVELOP", "SE16");
        defaultinstall.setProfile("DEVELOP");
        defaultinstall.setProgramAuthorization("DATAVIEWER");
    }
}
