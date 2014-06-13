package org.iocaste.infosis;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DummyElement;

public class ModelsInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) {
        ModelInstall model;
        DataElement name, value, username, terminal, conntime;
        
        name = elementchar("INFOSYS_NAME", 64, false);
        value = elementchar("INFOSYS_VALUE", 255, false);
        username = new DummyElement("LOGIN.USERNAME");
        terminal = elementchar("INFOSYS_TERMINAL", 20, true);
        conntime = elementdate("INFOSYS_DATE");
        
        model = modelInstance("INFOSYS_REPORT");
        model.item("NAME", name);
        model.item("VALUE", value);
        
        model = modelInstance("INFOSYS_SESSION");
        model.item("USERNAME", username);
        model.item("TERMINAL", terminal);
        model.item("STARTED", conntime);
    }

}
