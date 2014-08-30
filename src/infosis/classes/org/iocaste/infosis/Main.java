package org.iocaste.infosis;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.ViewContext;

public class Main extends AbstractPageBuilder {
    private static final String MAIN = "main";
    public static final String JVPR = "java-properties";
    public static final String SINF = "system-info";
    public static final String ULST = "users-list";
    public static final String UACC = "unauthorized-accesses";

    @Override
    public void config(PageBuilderContext context) {
        ViewContext view;
        Context extcontext = new Context();

        view = context.instance(MAIN);
        view.set(extcontext);
        view.set(new MainSpec());
        view.set(new MainConfig());
        view.put("items", new OptionChoosen());
        
        for (String page : new String[] {JVPR, SINF, ULST}) {
            view = context.instance(page);
            view.set(extcontext);
            view.set(new PropertiesSpec());
            view.set(new PropertiesConfig(page));
            view.set(new PropertiesInput());
        }
        
        view = context.instance(UACC);
        view.set(new UnauthorizedAccessesSpec());
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
