package org.iocaste.upload;

import org.iocaste.appbuilder.common.AbstractMessagesSource;
import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.AppBuilderLink;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.panel.StandardPanel;
import org.iocaste.upload.install.LayoutInstall;
import org.iocaste.upload.install.OptionsInstall;

public class Main extends AbstractPageBuilder {

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall) {
        AppBuilderLink link;
        
        defaultinstall.setProgramAuthorization("UPLOAD");
        defaultinstall.addToTaskGroup("UPLOAD", "UPLOAD");
        defaultinstall.addToTaskGroup("UPLOAD", "UPLLAYOUTCH");
        defaultinstall.addToTaskGroup("UPLOAD", "UPLLAYOUTDS");
        defaultinstall.addToTaskGroup("UPLOAD", "UPLLAYOUTCR");
        defaultinstall.setLink("UPLOAD", "iocaste-upload");
        defaultinstall.setProfile("ADMIN");
        
        link = defaultinstall.builderLinkInstance();
        link.cmodel = "UPL_LAYOUT";
        link.change = "UPLLAYOUTCH";
        link.display = "UPLLAYOUTDS";
        link.create = "UPLLAYOUTCR";
        link.entity = "layout";
        
        installObject("layout", new LayoutInstall());
        installObject("options", new OptionsInstall());
    }

    @Override
    public void config(PageBuilderContext context) throws Exception {
        StandardPanel panel;
        Context extcontext;
        
        extcontext = new Context(context);
        panel = new StandardPanel(context);
        panel.instance("main", new MainPage(), extcontext);
        
        context.addManager("layout", new LayoutManager(context.function));
        
        add(new Messages());
    }

}

class MainPage extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new MainSpec());
        set(new MainConfig());
        action("upload", new Upload());
    }
    
}

class Messages extends AbstractMessagesSource {
    
    public Messages() {
        put("columns", "Colunas");
        put("FILE", "Arquivo");
        put("IGNORE_FIRST_LINES", "Primeiras linhas ignoradas");
        put("iocaste-upload", "Utilitário de carga");
        put("LAYOUT", "Layout");
        put("TRUNCATE_CHAR", "Truncar caracteres excedentes");
        put("upload", "Carregar");
    }
}