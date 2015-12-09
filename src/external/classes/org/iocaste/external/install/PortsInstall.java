package org.iocaste.external.install;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ComplexModelInstall;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DummyModelItem;
import org.iocaste.packagetool.common.SearchHelpData;

public class PortsInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        ComplexModelInstall cmodel;
        ModelInstall model;
        DocumentModelItem porttypeid, portnameref, functionid;
        DataElement portname, porttype, text, host, sapprgid, sapgwserv;
        DataElement sapclient, sapsystemnumber, username, secret, portfunction;
        DataElement service, servicefunction, preconn, postconn;
        SearchHelpData shd;
        
        portname = elementchar(
                "XTRNL_PORT_NAME", 12, DataType.UPPERCASE);
        portfunction = elementchar(
                "XTRNL_PORT_FUNCT", 15, DataType.UPPERCASE);
        porttype = elementnumc(
                "XTRNL_PORT_TYPE", 2);
        text = elementchar(
                "XTRNL_TEXT", 45, DataType.KEEPCASE);
        host = elementchar(
                "XTRNL_HOST", 128, DataType.KEEPCASE);
        sapgwserv = elementchar(
                "XTRNL_SAPGWSERV", 64, DataType.KEEPCASE);
        sapprgid = elementchar(
                "XTRNL_SAPPRGID", 12, DataType.UPPERCASE);
        sapclient = elementchar(
                "XTRNL_SAPCLIENT", 3, DataType.UPPERCASE);
        sapsystemnumber = elementnumc(
                "XTRNL_SAPSYSNR", 2);
        username = elementchar(
                "XTRNL_USERNAME", 64, DataType.UPPERCASE);
        secret = elementchar(
                "XTRNL_SECRET", 64, DataType.KEEPCASE);
        service = elementchar(
                "XTRNL_SERVICE", 64, DataType.KEEPCASE);
        servicefunction = elementchar(
                "XTRNL_SERVICE_FUNCTION", 32, DataType.KEEPCASE);
        preconn = elementchar(
                "XTRNL_PRE_CONN", 128, DataType.KEEPCASE);
        postconn = elementchar(
                "XTRNL_POST_CONN", 128, DataType.KEEPCASE);
        
        model = modelInstance("XTRNL_PORT_TYPE", "XTRNLPORTTP");
        porttypeid = searchhelp(model.key(
                "PORT_TYPE", "PRTTP", porttype), "XTRNL_SH_PORTS_TYPES");
        model.item(
                "TEXT", "PRTTX", text);
        
        model.values(00, "Iocaste");
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
        model.item(
                "PRE_CONNECTION", "PRECN", preconn);
        model.item(
                "POST_CONNECTION", "PSTCN", postconn);
        
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
        model.item(
                "SERVICE", "SRVNM", service);
        model.item(
                "SERVICE_FUNCTION", "SRVFN", servicefunction);
        
        cmodel = cmodelInstance("XTRNL_CONNECTION");
        cmodel.header("porthead");
        cmodel.item("functions", "portfunction");
        
        /*
         * object model reference
         */
        model = modelInstance("XTRNL_IMPORT_OBJECT");
        searchhelp(model.reference(
                "NAME", new DummyModelItem("MODEL", "NAME")), "SH_MODEL");
        
        
    }

}
