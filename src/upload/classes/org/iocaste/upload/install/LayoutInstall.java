package org.iocaste.upload.install;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ComplexModelInstall;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DummyModelItem;
import org.iocaste.documents.common.SearchHelpData;

public class LayoutInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        ComplexModelInstall cmodel;
        ModelInstall model;
        DataElement layoutid, layoutitem, ignore, column;
        DocumentModelItem layoutkey, modelname, modelitem;
        SearchHelpData shd;
        
        layoutid = elementchar("UPL_LAYOUT_ID", 25, DataType.UPPERCASE);
        layoutitem = elementchar("UPL_COLUMN_KEY", 30, DataType.UPPERCASE);
        column = elementchar("UPL_COLUMN_NAME", 30, DataType.UPPERCASE);
        ignore = elementbool("UPL_COLUMN_IGNORE");
        
        modelname = new DummyModelItem("MODEL", "NAME");
        modelitem = new DummyModelItem("MODELITEM", "ID");
        
        /*
         * head
         */
        model = tag("head", modelInstance(
                "UPL_LAYOUT_HEAD", "UPLLAYOUTHD"));
        layoutkey = tag("layoutkey", searchhelp(model.key(
                "ID", "LAYID", layoutid), "UPL_SH_LAYOUT"));
        searchhelp(model.reference(
                "MODEL", "MODEL", modelname), "SH_MODEL");
        
        /*
         * columns
         */
        model = tag("columns", modelInstance(
                "UPL_LAYOUT_COLUMN", "UPLAYOUTCOL"));
        model.key(
                "COLUMN_ID", "COLID", layoutitem);
        model.reference(
                "LAYOUT", "LAYID", layoutkey);
        model.item(
                "NAME", "COLNM", column);
        searchhelp(model.reference(
                "MODEL_ITEM", "MDLCL", modelitem), "UPL_SH_MODEL_ITEM");
        model.item(
                "IGNORE", "IGNRE", ignore);
        
        shd = searchHelpInstance("UPL_SH_MODEL_ITEM", "MODELITEM");
        shd.setExport("ID");
        shd.add("ID");
        shd.add("NAME");
        shd.add("MODEL");
        
        shd = searchHelpInstance("UPL_SH_LAYOUT", "UPL_LAYOUT_HEAD");
        shd.setExport("ID");
        shd.add("ID");
        shd.add("MODEL");
        
        cmodel = cmodelInstance("UPL_LAYOUT");
        cmodel.header("head");
        cmodel.item("columns", "columns").keydigits = 4;
    }

}
