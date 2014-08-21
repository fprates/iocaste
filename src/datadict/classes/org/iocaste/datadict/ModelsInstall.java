package org.iocaste.datadict;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;

public class ModelsInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) {
        ModelInstall model;
        DataElement key;
        
        key = elementbool("DD_KEY");
        
        model = modelInstance("DD_MODEL_ITEM");
        model.item("NAME", reference("MODELITEM.NAME"));
        model.item("FIELDNAME", reference("MODELITEM.FIELDNAME"));
        model.item("KEY", key);
        model.item("ELEMENT", reference("DATAELEMENT.NAME"));
        model.item("TYPE", reference("DATAELEMENT.TYPE"));
        model.item("LENGTH", reference("DATAELEMENT.LENGTH"));
        model.item("DECIMALS", reference("DATAELEMENT.DECIMALS"));
    }

}
