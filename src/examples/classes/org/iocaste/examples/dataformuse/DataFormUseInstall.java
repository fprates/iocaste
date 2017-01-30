package org.iocaste.examples.dataformuse;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;

public class DataFormUseInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        ModelInstall model;
        DataElement name, gift, adjective;
        
        name = elementchar("EXAMPLES_DFUSE_NAME", 48, DataType.KEEPCASE);
        gift = elementchar("EXAMPLES_DFUSE_GIFT", 24, DataType.KEEPCASE);
        adjective = elementchar("EXAMPLES_DFUSE_ADJCTV", 24, DataType.KEEPCASE);
        
        model = modelInstance("EXAMPLES_DFUSE_INPUT");
        model.item("NAME", name);
        model.item("GIFT", gift);
        model.item("ADJECTIVE", adjective);
    }

}
