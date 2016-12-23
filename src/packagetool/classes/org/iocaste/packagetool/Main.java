package org.iocaste.packagetool;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.packagetool.detail.DetailInput;
import org.iocaste.packagetool.detail.DetailSpec;
import org.iocaste.packagetool.main.MainPanel;
import org.iocaste.packagetool.services.IsInstalled;

public class Main extends AbstractPageBuilder {
    
    public Main() {
        super();
        export("is_installed", new IsInstalled());
    }
    
    @Override
    public void config(PageBuilderContext context) throws Exception {
        Context extcontext;
        
        extcontext = new Context(context);
        context.messages = new Messages();
        context.add("main", new MainPanel(), extcontext);
        context.add("detail", new DetailPanel(), extcontext);
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        defaultinstall.setLink("PACKAGE", "iocaste-packagetool");
        defaultinstall.addToTaskGroup("ADMIN", "PACKAGE");
        defaultinstall.setProfile("ALL");
        defaultinstall.setProgramAuthorization("PACKAGE.EXECUTE");
        
        installObject("tasks", new TaskInstall());
        installObject("tasksgroups", new TasksGroupsInstall());
        installObject("usergroups", new UserTaskGroupsInstall());
        installObject("package", new PackageInstall());
    }
    
    public static final void registerException(
            String pkgname, Context extcontext, Exception e) {
        Throwable cause;
        
        cause = e.getCause();
        extcontext.exceptions.put(pkgname, (cause == null)? e : cause);
    }
}


class DetailPanel extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new DetailSpec());
        set(new DetailInput());
    }
    
}