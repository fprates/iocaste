package org.iocaste.exhandler;

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
        
        extcontext = new Context();
        extcontext.ex = getParameter("exception");
        extcontext.exview = getParameter("exview");
        extcontext.messages.put("unknown", "desconhecido");
        extcontext.messages.put("page", "Página");
        extcontext.messages.put("module", "Módulo");
        extcontext.messages.put("exception-handler", "Erro durante execução");
        extcontext.messages.put("view-info", "Visão afetada");
        extcontext.messages.put("stack-trace", "Pilha de chamadas");
        extcontext.messages.put("parameters", "Parâmetros");
        extcontext.messages.put("exception", "Exceção");
        extcontext.messages.put("view-elements", "Elementos da visão afetada");
        extcontext.messages.put("no.view.information",
                "Sem informações da visão\n");
        
        panel = new StandardPanel(context);
        panel.instance("main", new MainPage(), extcontext);
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        // TODO Auto-generated method stub
        
    }
}

class MainPage extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new MainSpec());
        set(new MainConfig());
        set(new MainInput());
    }
    
}