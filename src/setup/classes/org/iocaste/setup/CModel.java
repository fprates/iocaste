package org.iocaste.setup;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DummyModelItem;

public class CModel extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        ModelInstall model;
        DataElement cmodelname, cmodelitemid, cmodelitemname;
        DummyModelItem modelname;
        DocumentModelItem cmodelkey;

        cmodelname = elementchar("CMODEL_NAME", 24, true);
        modelname = new DummyModelItem("MODEL", "NAME");
        cmodelitemid = elementchar("CMODEL_ITEM_IDENT", 49, false);
        cmodelitemname = elementchar("CMODEL_ITEM_NAME", 24, false);
        
        /*
         * Modelo de documento complexo
         */
        model = modelInstance("COMPLEX_MODEL", "CPLXMODEL");
        cmodelkey = model.key("NAME", "CMNAM", cmodelname);
        model.reference("MODEL", "MODEL", modelname);
        
        /*
         * Item de modelo de documento complexo
         */
        model = modelInstance("COMPLEX_MODEL_ITEM", "CPLXMITEM");
        model.key("IDENT", "CMIID", cmodelitemid);
        model.item("NAME", "CMINM", cmodelitemname);
        model.reference("CMODEL", "MDLNM", cmodelkey);
        model.reference("MODEL", "MODEL", modelname);
    }
}
