package org.iocaste.tasksel;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;

public class InstallObject extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        ModelInstall model;
        DataElement group, name, text;

        group = elementchar("TASK_TILE_GROUP", 64, DataType.UPPERCASE);
        name = elementchar("TASK_TILE_NAME", 64, DataType.UPPERCASE);
        text = elementchar("TASK_TILE_TEXT", 128, DataType.KEEPCASE);
        
        model = modelInstance("TASK_TILE_ENTRY");
        model.item("GROUP", group);
        model.item("NAME", name);
        model.item("TEXT", text);
    }
}

