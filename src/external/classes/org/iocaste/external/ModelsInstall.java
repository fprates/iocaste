package org.iocaste.external;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ComplexModelInstall;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.packagetool.common.SearchHelpData;

public class ModelsInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        ComplexModelInstall cmodel;
        ModelInstall model;
        DocumentModelItem porttypeid;
        DataElement portname, porttype, text;
        SearchHelpData shd;
        
        portname = new DataElement("XTRNL_PORT_NAME");
        portname.setType(DataType.CHAR);
        portname.setLength(12);
        portname.setUpcase(true);
        
        porttype = new DataElement("XTRNL_PORT_TYPE");
        porttype.setType(DataType.NUMC);
        porttype.setLength(2);
        
        text = new DataElement("XTRNL_TEXT");
        text.setType(DataType.CHAR);
        text.setLength(45);
        text.setUpcase(false);
        
        model = modelInstance("XTRNL_PORT_TYPE", "XTRNLPORTTP");
        porttypeid = searchhelp(model.key(
                "PORT_TYPE", "PRTTP", porttype), "XTRNL_SH_PORTS_TYPES");
        model.item(
                "TEXT", "PRTTX", text);
        
        model.values(01, "SAP");
        
        shd = searchHelpInstance("XTRNL_SH_PORTS_TYPES", "XTRNL_PORT_TYPE");
        shd.setExport("PORT_TYPE");
        shd.add("PORT_TYPE");
        shd.add("TEXT");
        
        model = tag("head", modelInstance(
                "XTRNL_PORT_HEAD", "XTRNLPORTHD"));
        searchhelp(model.key(
                "PORT_NAME", "CONID", portname), "XTRNL_SH_PORTS");
        searchhelp(model.reference(
                "PORT_TYPE", "PRTTP", porttypeid), "XTRNL_SH_PORTS_TYPES");
        model.item(
                "TEXT", "PRTTX", text);
        
        shd = searchHelpInstance("XTRNL_SH_PORTS", "XTRNL_PORT_HEAD");
        shd.setExport("PORT_NAME");
        shd.add("PORT_NAME");
        shd.add("TEXT");
        
        cmodel = cmodelInstance("XTRNL_CONNECTION");
        cmodel.header("head");
    }

}
