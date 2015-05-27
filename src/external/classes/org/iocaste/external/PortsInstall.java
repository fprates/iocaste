package org.iocaste.external;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ComplexModelInstall;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.packagetool.common.SearchHelpData;

public class PortsInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        ComplexModelInstall cmodel;
        ModelInstall model;
        DocumentModelItem porttypeid, portnameref, functionid;
        DataElement portname, porttype, text, host, sapprgid, sapgwserv;
        DataElement sapclient, sapsystemnumber, username, secret, portfunction;
        SearchHelpData shd;
        
        portname = new DataElement("XTRNL_PORT_NAME");
        portname.setType(DataType.CHAR);
        portname.setLength(12);
        portname.setUpcase(true);
        
        portfunction = new DataElement("XTRNL_PORT_FUNCT");
        portfunction.setType(DataType.CHAR);
        portfunction.setLength(15);
        portfunction.setUpcase(true);
        
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
        
        sapgwserv = new DataElement("XTRNL_SAPGWSERV");
        sapgwserv.setType(DataType.CHAR);
        sapgwserv.setLength(64);
        sapgwserv.setUpcase(false);
        
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
        
        /*
         * cabeçalho
         */
        model = tag("porthead", modelInstance(
                "XTRNL_PORT_HEAD", "XTRNLPORTHD"));
        portnameref = searchhelp(model.key(
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
                "SAP_GWSERVER", "SAPGWSERV", sapgwserv);
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
        
        /*
         * funções
         */
        functionid = getItem("functionid");
        model = tag("portfunction", modelInstance(
                "XTRNL_PORT_FUNCTION", "XTRNLPRTFNC"));
        model.key(
                "PORT_FUNCTION", "PRTFN", portfunction);
        model.reference(
                "PORT_NAME", "CONID", portnameref);
        searchhelp(model.reference(
                "FUNCTION", "FUNCT", functionid), "XTRNL_SH_FUNCTION");
        
        cmodel = cmodelInstance("XTRNL_CONNECTION");
        cmodel.header("porthead");
        cmodel.item("functions", "portfunction");
    }

}
