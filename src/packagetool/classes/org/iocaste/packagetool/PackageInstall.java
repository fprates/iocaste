package org.iocaste.packagetool;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DummyElement;

public class PackageInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        ModelInstall model;
        DataElement exception;
        
        exception = elementchar("PACKAGE_EXCEPTION", 255, DataType.KEEPCASE);
        
        model = modelInstance("PACKAGE_GRID");
        model.item("NAME", new DummyElement("PACKAGE.NAME"));
        model.item("EXCEPTION", exception);
    }

}
