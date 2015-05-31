package org.iocaste.usereditor;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.DataForm;

public class UpdateConfig extends DetailConfig {

    @Override
    protected void config(
            DataForm identity, TableToolData tasks, TableToolData profiles) {
        tasks.mode = TableTool.UPDATE;
        tasks.mark = true;
        
        profiles.mode = TableTool.UPDATE;
        profiles.mark = true;
        
        getNavControl().setTitle("usereditor-update");
    }

}
