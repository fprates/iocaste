package org.iocaste.infosis;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

public class Main extends AbstractPageBuilder {
    public static final String[] ACTIONS = {
        "java-properties",
        "system-info",
        "users-list",
        "unauthorized-accesses"
    };
    
    private static final String MAIN = "main";
    public static final int JVPR = 0;
    public static final int SINF = 1;
    public static final int ULST = 2;
//    public static final int UACC = 3;

    @Override
    public void config(PageBuilderContext context) {
        context.add(MAIN, new MainPage(), new Context(context));
        context.messages = new Messages();
//        int i;
//        ViewContext view;
//        Context extcontext = new Context();
//
//        view = context.instance(MAIN);
//        view.set(extcontext);
//        view.set(new MainSpec());
//        view.set(new MainConfig());
//        view.set(new MainInput());
//        
//        for (i = 0; i <= ULST; i++)
//            view.put(ACTIONS[i], new OptionChoosen(i));
//        
//        view = context.instance("report");
//        view.set(extcontext);
//        view.set(new PropertiesSpec());
//        view.set(new PropertiesConfig());
//        view.set(new PropertiesInput());
//        
//        view = context.instance(ACTIONS[UACC]);
//        view.set(new UnauthorizedAccessesSpec());
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        defaultinstall.setProfile("ADMIN");
        defaultinstall.setProgramAuthorization("INFOSYS.EXECUTE");
        defaultinstall.setLink("SYSINFO", "iocaste-infosis");
        defaultinstall.addToTaskGroup("ADMIN", "SYSINFO");
        
        installObject("models", new ModelsInstall());
    }
}

class MainPage extends AbstractPanelPage {

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        
    }
    
}
