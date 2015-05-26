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
        DataElement portname, porttype, text, host, sapprgid;
        DataElement sapclient, sapsystemnumber, username, secret;
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
        
        host = new DataElement("XTRNL_HOST");
        host.setType(DataType.CHAR);
        host.setLength(128);
        host.setUpcase(false);
        
        sapprgid = new DataElement("XTRNL_SAPPRGID");
        sapprgid.setType(DataType.CHAR);
        sapprgid.setLength(12);
        sapprgid.setUpcase(true);
        
        sapclient = new DataElement("XTRNL_SAPCLIENT");
        sapclient.setType(DataType.CHAR);
        sapclient.setLength(3);
        sapclient.setUpcase(true);
        
        sapsystemnumber = new DataElement("XTRNL_SAPSYSNR");
        sapsystemnumber.setType(DataType.NUMC);
        sapsystemnumber.setLength(2);
        
        username = new DataElement("XTRNL_USERNAME");
        username.setType(DataType.CHAR);
        username.setLength(64);
        username.setUpcase(true);
        
        secret = new DataElement("XTRNL_SECRET");
        secret.setType(DataType.CHAR);
        secret.setLength(64);
        secret.setUpcase(false);
        
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
        model.item(
                "HOST", "HOST", host);
        model.item(
                "SAP_GWHOST", "SAPGWHOST", host);
        model.item(
                "SAP_PROGRAM_ID", "SAPPRGID", sapprgid);
        model.item(
                "SAP_CLIENT", "SAPCLIENT", sapclient);
        model.item(
                "USERNAME", "USRNM", username);
        model.item(
                "SECRET", "SECRT", secret);
        model.item(
                "SAP_SYSTEM_NUMBER", "SAPSYSNR", sapsystemnumber);
        
        shd = searchHelpInstance("XTRNL_SH_PORTS", "XTRNL_PORT_HEAD");
        shd.setExport("PORT_NAME");
        shd.add("PORT_NAME");
        shd.add("TEXT");
        
        cmodel = cmodelInstance("XTRNL_CONNECTION");
        cmodel.header("head");
    }

}
