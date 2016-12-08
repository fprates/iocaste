package org.iocaste.workbench.install;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DummyElement;
import org.iocaste.documents.common.SearchHelpData;

public class DataElementsInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) {
        ModelInstall model;
        DataElement dename, detype, desize, dedec, deupcase;
        DataElement datatypetext;
        DocumentModelItem datatypekey;
        SearchHelpData shd;
        
        datatypetext = elementchar("WB_DATA_TYPE_TEXT", 20, DataType.KEEPCASE);
        dename = new DummyElement("DATAELEMENT.NAME");
        detype = new DummyElement("DATAELEMENT.TYPE");
        desize = new DummyElement("DATAELEMENT.LENGTH");
        dedec = new DummyElement("DATAELEMENT.DECIMALS");
        deupcase = new DummyElement("DATAELEMENT.UPCASE");
        
        
        model = modelInstance(
                "WB_DATA_TYPE", "WBDATATYPE");
        datatypekey = model.key(
                "TYPE_ID", "DTPID", detype);
        model.item(
                "TEXT", "DTPTX", datatypetext);
        
        model.values(DataType.CHAR, "Caracter");
        model.values(DataType.DATE, "Data");
        model.values(DataType.DEC, "Decimal");
        model.values(DataType.NUMC, "Numérico");
        model.values(DataType.TIME, "Hora");
        model.values(DataType.BOOLEAN, "Booleano");
        model.values(DataType.BYTE, "Byte");
        model.values(DataType.INT, "Inteiro");
        model.values(DataType.LONG, "Inteiro longo");
        model.values(DataType.SHORT, "Short");
        
        shd = searchHelpInstance("SH_WB_DATA_TYPE", "WB_DATA_TYPE");
        shd.setExport("TYPE_ID");
        shd.add("TYPE_ID");        
        shd.add("TEXT");
        
        /*
         * data elements
         */
        model = modelInstance(
                "WB_DATA_ELEMENTS", "WBDATAELEMENTS");
        tag("dataelementkey", model.key(
                "NAME", "DELNM", dename));
        model.reference(
                "PROJECT", "PRJNM", getItem("projectkey"));
        searchhelp(model.reference(
                "TYPE", "DELTY", datatypekey), "SH_WB_DATA_TYPE");
        model.item(
                "SIZE", "DELEN", desize);
        model.item(
                "DECIMALS", "DEDEC", dedec);
        model.item(
                "UPCASE", "DEUPC", deupcase);
        
        shd = searchHelpInstance("SH_WB_DATA_ELEMENT", "WB_DATA_ELEMENTS");
        shd.setExport("NAME");
        shd.add("NAME");
        shd.add("TYPE");
        shd.add("SIZE");
    }
    
}

