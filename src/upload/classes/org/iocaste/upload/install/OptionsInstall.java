package org.iocaste.upload.install;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;

public class OptionsInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        ModelInstall model;
        DataElement ignore, file;
        
        file = elementchar("UPL_FILE", 255, DataType.KEEPCASE);
        ignore = elementnumc("UPL_IGNORE_LINES", 2);
        
        model = modelInstance("UPL_OPTIONS");
        model.item(
                "FILE", file);
        searchhelp(model.item(
                "LAYOUT", getItem("layoutkey")), "UPL_SH_LAYOUT");
        model.item(
                "IGNORE_FIRST_LINES", ignore);
    }

}
