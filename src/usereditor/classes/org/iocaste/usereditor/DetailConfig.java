package org.iocaste.usereditor;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolItem;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.Const;

public abstract class DetailConfig extends AbstractViewConfig {
    
    protected abstract void config(
            DataFormToolData identity, DataFormToolData extras,
            TableToolData tasks, TableToolData profiles);

    @Override
    public final void execute(PageBuilderContext context) {
        TableToolData tasks, profiles;
        DataFormToolData identity, extras;
        DataFormToolItem item;
        
        identity = getTool("identity");
        identity.model = "LOGIN";
        identity.instance("ID").invisible = true;
        identity.instance("INIT").componenttype = Const.CHECKBOX;
        identity.instance("USERNAME").disabled = true;
        
        item = identity.instance("SECRET");
        item.secret = item.focus = true;
        
        extras = getTool("extras");
        extras.model = "LOGIN_EXTENSION";
        extras.instance("USERNAME").invisible = true;
        
        tasks = getTool("tasks");
        tasks.model = "USER_TASKS_GROUPS";
        for (String name : new String[] {"ID", "USERNAME", "PACKAGE"})
            tasks.instance(name).invisible = true;
        tasks.instance("GROUP").sh = "SH_TASKS_GROUPS";

        profiles = getTool("profiles");
        profiles.model = "USER_AUTHORITY";
        for (String name : new String[] {"ID", "USERNAME"})
            profiles.instance(name).invisible = true;
        profiles.instance("PROFILE").sh = "SH_USER_PROFILE";
        
        config(identity, extras, tasks, profiles);
    }
}
