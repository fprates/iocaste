package org.iocaste.usereditor;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;

public class DisplayConfig extends DetailConfig {

    @Override
    protected void config(DataForm identity, DataForm extras,
            TableToolData tasks, TableToolData profiles) {
        for (Element element : identity.getElements())
            element.setEnabled(false);
        
        for (Element element : extras.getElements())
            element.setEnabled(false);
        
        tasks.mode = TableTool.DISPLAY;
        profiles.mode = TableTool.DISPLAY;
        getNavControl().setTitle("usereditor-display");
    }

}
