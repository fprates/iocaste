package org.iocaste.external.install;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ComplexModelInstall;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.SearchHelpData;

public class FunctionsInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        ModelInstall model;
        ComplexModelInstall cmodel;
        DocumentModelItem modelref, structureref, typeref;
        DataElement modelid, modeltext, parameterid, structitem;
        DataElement parametername, parametertype, structname;
        SearchHelpData shd;

        structname = elementchar("XTRNL_STRUCT_ID", 32, DataType.UPPERCASE);
        structitem = elementchar("XTRNL_STRUCT_ITEM", 35, DataType.UPPERCASE);
        
        modelid = elementchar("XTRNL_MODEL_ID", 32, DataType.UPPERCASE);
        modeltext = elementchar("XTRNL_MODEL_TX", 60, DataType.KEEPCASE);
        parameterid = elementchar("XTRNL_PARAM_ID", 35, DataType.UPPERCASE);
        parametername = elementchar("XTRNL_PARAM_NAME", 32, DataType.UPPERCASE);
        parametertype = elementnumc("XTRNL_PARAM_TYPE", 2);
        
        model = modelInstance(
                "XTRNL_ITEM_TYPE", "XTRNLITEMTP");
        typeref = model.key(
                "TYPE", "DTTYP", parametertype);
        model.item(
                "TEXT", "TYPNM", modeltext);
        
        model.values(DataType.BYTE, "Byte");
        model.values(DataType.CHAR, "Carater n posições");
        model.values(DataType.DATE, "Data");
        model.values(DataType.DEC, "Decimal ponto fixo");
        model.values(DataType.INT, "Inteiro");
        model.values(DataType.LONG, "Inteiro longo");
        model.values(DataType.NUMC, "Inteiro posição fixa");
        model.values(DataType.SHORT, "Inteiro");
        model.values(DataType.TIME, "Hora");
        model.values(DataType.EXTENDED, "Objeto extendido");
        model.values(DataType.TABLE, "Tabela");
        model.values(DataType.BOOLEAN, "Booleano");
        
        shd = searchHelpInstance("XTRNL_SH_ITEM_TYPE", "XTRNL_ITEM_TYPE");
        shd.setExport("TYPE");
        shd.add("TYPE");
        shd.add("TEXT");
        
        /*
         * estruturas
         */
        model = tag("structhead", modelInstance(
                "XTRNL_STRUCTURE_HEAD", "XTRNLSTRCTHD"));
        structureref = searchhelp(model.key(
                "NAME", "STRNM", structname), "XTRNL_SH_STRUCT");
        model.item(
                "TEXT", "STRTX", modeltext);
        
        model = tag("structitem", modelInstance(
                "XTRNL_STRUCTURE_ITEM", "XTRNLSTRUCIT"));
        model.key(
                "ITEM_ID", "POSID", structitem);
        model.reference(
                "STRUCTURE", "STRID", structureref);
        model.item(
                "NAME", "POSNM", parametername);
        searchhelp(model.reference(
                "TYPE", "ITMTP", typeref), "XTRNL_SH_ITEM_TYPE");

        shd = searchHelpInstance("XTRNL_SH_STRUCT", "XTRNL_STRUCTURE_HEAD");
        shd.setExport("NAME");
        shd.add("NAME");
        shd.add("TEXT");
        
        cmodel = cmodelInstance("XTRNL_STRUCTURE");
        cmodel.header("structhead");
        cmodel.item("items", "structitem").index = "NAME";
        
        /*
         * função
         */
        model = tag("functionhead", modelInstance(
                "XTRNL_FUNCTION_HEAD", "XTRNLFNCHD"));
        modelref = searchhelp(tag("functionid", model.key(
                "MODEL_ID", "MDLID", modelid)), "XTRNL_SH_FUNCTION");
        model.item(
                "TEXT", "MDLTX", modeltext);
        
        model = tag("functionitem", modelInstance(
                "XTRNL_FUNCTION_ITEM", "XTRNLFNCIT"));
        model.key(
                "ITEM_ID", "ITMID", parameterid);
        model.reference(
                "MODEL_ID", "MDLID", modelref);
        model.item(
                "NAME", "PARNM", parametername);
        searchhelp(model.reference(
                "TYPE", "PARTP", typeref), "XTRNL_SH_ITEM_TYPE");
        searchhelp(model.reference(
                "STRUCTURE", "STRNM", structureref), "XTRNL_SH_STRUCT");
        
        shd = searchHelpInstance("XTRNL_SH_FUNCTION", "XTRNL_FUNCTION_HEAD");
        shd.setExport("MODEL_ID");
        shd.add("MODEL_ID");
        shd.add("TEXT");
        
        cmodel = cmodelInstance("XTRNL_FUNCTION");
        cmodel.header("functionhead");
        cmodel.item("parameters", "functionitem").index = "NAME";
    }

}
