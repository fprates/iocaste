package org.iocaste.copy;

import org.iocaste.appbuilder.common.AbstractMessagesSource;
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
        
        add(new Messages());
        
        extcontext = new Context(context);
        panel = new StandardPanel(context);
        panel.instance("main", new MainPage(), extcontext);
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        defaultinstall.setLink("COPY", "iocaste-copy");
        defaultinstall.addToTaskGroup("ADMIN", "COPY");
        defaultinstall.setProgramAuthorization("COPY");
        defaultinstall.setProfile("ADMIN");
    }

}

class MainPage extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new InputSpec());
        set(new InputConfig());
        submit("copy", new Copy());
    }
    
}

class Messages extends AbstractMessagesSource {
    
    public Messages() {
        put("COPY", "Copiar");
        put("NAME", "Modelo");
        put("NAMESPACE", "Namespace");
        put("no.records", "Sem registros para copiar.");
        put("PORT_NAME", "Porta de comunicação");
        put("sucessful.copy", "Cópia bem sucedida.");
    }
}