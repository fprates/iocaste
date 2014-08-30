package org.iocaste.infosis;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;

public class Main extends AbstractPageBuilder {
    private static final String MAIN = "main";
    public static final String JVPR = "java-properties";
    public static final String SINF = "system-info";
    public static final String ULST = "users-list";
    public static final String UACC = "unauthorized-accesses";

    @Override
    public void config(PageBuilderContext context) {
        Context extcontext = new Context();

        context.setExtendedContext(MAIN, extcontext);
        context.setView(MAIN, new MainSpec(), new MainConfig());
        context.setActionHandler(MAIN, "items", new OptionChoosen());
        
        for (String page : new String[] {JVPR, SINF, ULST}) {
            context.setExtendedContext(page, extcontext);
            context.setView(page,
                    new PropertiesSpec(),
                    new PropertiesConfig(page),
                    new PropertiesInput());
        }
        
        context.setView(UACC, new UnauthorizedAccessesSpec());
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        defaultinstall.setProfile("ADMIN");
        defaultinstall.setProgramAuthorization("INFOSYS.EXECUTE");
        defaultinstall.setLink("SYSINFO", "iocaste-infosis");
        defaultinstall.addToTaskGroup("ADMIN", "SYSINFO");
        
        installObject("models", new ModelsInstall());
        installObject("messages", new MessagesInstall());
    }
}
