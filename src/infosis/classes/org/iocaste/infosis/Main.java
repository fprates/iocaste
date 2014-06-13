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

        context.addExtendedContext(MAIN, extcontext);
        context.setViewSpec(MAIN, new MainSpec());
        context.setViewConfig(MAIN, new MainConfig());
        context.setActionHandler(MAIN, "items", new OptionChoosen());
        
        for (String page : new String[] {JVPR, SINF, ULST}) {
            context.addExtendedContext(page, extcontext);
            context.setViewSpec(page, new PropertiesSpec());
            context.setViewConfig(page, new PropertiesConfig(page));
            context.setViewInput(page, new PropertiesInput());
        }
        
        context.setViewSpec(UACC, new UnauthorizedAccessesSpec());
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        defaultinstall.setTaskGroup("ADMIN");
        defaultinstall.setProfile("ADMIN");
        defaultinstall.setProgramAuthorization("INFOSYS.EXECUTE");
        defaultinstall.setLink("SYSINFO", "iocaste-infosis");
        
        installObject("models", new ModelsInstall());
        installObject("messages", new MessagesInstall());
    }
}
