package org.iocaste.usereditor;

import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;

public class DisplayConfig extends DetailConfig {

    @Override
    protected void config(DataFormToolData identity, DataFormToolData extras,
            TableToolData tasks, TableToolData profiles) {
        identity.disabled = true;
        extras.disabled = true;
        
        tasks.mode = TableTool.DISPLAY;
        profiles.mode = TableTool.DISPLAY;
        getNavControl().setTitle("usereditor-display");
    }

}
