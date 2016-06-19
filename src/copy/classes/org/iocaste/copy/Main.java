package org.iocaste.copy;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.panel.StandardPanel;
import org.iocaste.shell.common.MessageSource;

public class Main extends AbstractPageBuilder {

    @Override
    public void config(PageBuilderContext context) throws Exception {
        StandardPanel panel;
        Context extcontext;
        
        new Messages(context.messages);
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

class Messages {
    
    public Messages(MessageSource messages) {
        messages.put("COPY", "Copiar");
        messages.put("NAME", "Modelo");
        messages.put("NAMESPACE", "Namespace");
        messages.put("no.records", "Sem registros para copiar.");
        messages.put("PORT_NAME", "Porta de comunicação");
        messages.put("sucessful.copy", "Cópia bem sucedida.");
    }
}