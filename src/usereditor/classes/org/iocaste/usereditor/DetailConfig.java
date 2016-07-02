package org.iocaste.usereditor;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolItem;
import org.iocaste.appbuilder.common.tabletool.TableToolColumn;
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
        identity.itemInstance("ID").invisible = true;
        identity.itemInstance("INIT").componenttype = Const.CHECKBOX;
        identity.itemInstance("USERNAME").disabled = true;
        
        item = identity.itemInstance("SECRET");
        item.secret = item.focus = true;
        
        extras = getTool("extras");
        extras.model = "LOGIN_EXTENSION";
        extras.itemInstance("USERNAME").invisible = true;
        
        tasks = getTool("tasks");
        tasks.model = "USER_TASKS_GROUPS";
        tasks.show = new String[] {"GROUP"};
        new TableToolColumn(tasks, "GROUP").sh = "SH_TASKS_GROUPS";

        profiles = getTool("profiles");
        profiles.model = "USER_AUTHORITY";
        profiles.show = new String[] {"PROFILE"};
        new TableToolColumn(profiles, "PROFILE").sh = "SH_USER_PROFILE";
        
        config(identity, extras, tasks, profiles);
    }
}
