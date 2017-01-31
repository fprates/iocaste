package org.iocaste.examples.tabletooluse;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;

public class TableToolUseInstall extends AbstractInstallObject {

    @Override
    public final void execute(StandardInstallContext context) {
        ModelInstall model;
        DataElement name, age, marriedon;
        
        name = elementchar("EXAMPLES_TTUSE_NAME", 64, DataType.KEEPCASE);
        age = elementnumc("EXAMPLES_TTUSE_AGE", 2);
        marriedon = elementdate("EXAMPLES_TTUSE_MARRIEDON");
        
        model = modelInstance("EXAMPLES_TTUSE_ITEM");
        model.item("NAME", name);
        model.item("AGE", age);
        model.item("MARRIED_ON", marriedon);
    }
}
