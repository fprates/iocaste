package org.iocaste.workbench.install;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ComplexModelInstall;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DummyElement;

public class ModelsInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) {
        ModelInstall model;
        DocumentModelItem modelid;
        ComplexModelInstall cmodel;
        DataElement modelitemid, modelitemname, modeltable, modelkey, modelname;
        DataElement tableitemname;

        modelname = new DummyElement("MODEL.NAME");
        modeltable = new DummyElement("MODEL.TABLE");
        tableitemname = new DummyElement("MODELITEM.FIELDNAME");
        modelitemid = elementchar("WB_MODEL_ITEM", 38, true);
        modelitemname = elementchar("WB_MODEL_ITEM_NAME", 24, true);
        modelkey = elementbool("WB_BOOLEAN");
        
        /*
         * model header
         */
        model = tag("model_head", modelInstance(
                "WB_MODEL_HEADER", "WBMODELHD"));
        modelid = model.key(
                "NAME", "MDLNM", modelname);
        model.reference(
                "PROJECT", "PRJNM", getItem("projectkey"));
        model.item(
                "TABLE", "MDLTB", modeltable);
        
        /*
         * model item
         */
        model = tag("model_item", modelInstance(
                "WB_MODEL_ITEMS", "WBMODELIT"));
        model.key(
                "ITEM_ID", "ITMID", modelitemid);
        model.reference(
                "PROJECT", "PRJNM", getItem("projectkey"));
        model.reference(
                "MODEL", "MDLID", modelid);
        model.item(
                "NAME", "ITMNM", modelitemname);
        model.item(
                "FIELD", "FLDNM", tableitemname);
        searchhelp(model.reference(
                "DATA_ELEMENT", "DTELM", getItem("dataelementkey")),
                "SH_WB_DATA_ELEMENT");
        model.item(
                "KEY", "MDKEY", modelkey);
        
        cmodel = tag("models", cmodelInstance("WB_MODELS"));
        cmodel.header("model_head");
        cmodel.item("item", "model_item").index = "NAME";
    }
    
}