package org.iocaste.usereditor;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.tabletool.TableToolColumn;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;

public abstract class DetailConfig extends AbstractViewConfig {
    
    protected abstract void config(DataForm identity, DataForm extras,
            TableToolData tasks, TableToolData profiles);

    @Override
    public final void execute(PageBuilderContext context) {
        TableToolData tasks, profiles;
        InputComponent input;
        DataForm identity, extras;
        
        identity = getElement("identity");
        identity.importModel("LOGIN", context.function);
        identity.get("ID").setVisible(false);
        
        input = identity.get("INIT");
        input.setComponentType(Const.CHECKBOX);
        
        input = identity.get("SECRET");
        input .setSecret(true);
        context.view.setFocus(input);
        
        input = identity.get("USERNAME");
        input.setEnabled(false);
        
        extras = getElement("extras");
        extras.importModel("LOGIN_EXTENSION", context.function);
        extras.get("USERNAME").setVisible(false);
        
        tasks = getTableTool("tasks");
        tasks.model = "USER_TASKS_GROUPS";
        tasks.show = new String[] {"GROUP"};
        new TableToolColumn(tasks, "GROUP").sh = "SH_TASKS_GROUPS";

        profiles = getTableTool("profiles");
        profiles.model = "USER_AUTHORITY";
        profiles.show = new String[] {"PROFILE"};
        new TableToolColumn(profiles, "PROFILE").sh = "SH_USER_PROFILE";
        
        config(identity, extras, tasks, profiles);
    }
}
