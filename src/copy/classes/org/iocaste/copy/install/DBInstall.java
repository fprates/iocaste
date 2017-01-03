package org.iocaste.copy.install;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;

public class DBInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        ModelInstall model;
        DataElement database;
        
        database = elementchar("COPY_DB_NAME", 12, DataType.UPPERCASE);
        
        model = modelInstance("COPY_EXTERNAL_DB");
        model.item("DATABASE", database);
    }

}
