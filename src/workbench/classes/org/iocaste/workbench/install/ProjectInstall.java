package org.iocaste.workbench.install;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ComplexModelInstall;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DummyElement;
import org.iocaste.packagetool.common.SearchHelpData;

public class ProjectInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        DataElement projectname, projectscreen, text, screenname, modelname;
        DataElement screenspecitemid, screenspecitemname, projectmodel;
        DataElement modelitemid, modelitemname, typeid, typetext, command;
        DataElement modelitemlength, screenitemtype, linkid, linkname;
        ModelInstall model;
        ComplexModelInstall cmodel;
        DocumentModelItem project, screen, modelid, datatype;
        SearchHelpData shd;

        projectname = elementchar("WB_PROJECT_NAME", 32, true);
        text = elementchar("WB_TEXT", 32, false);
        projectscreen = elementchar("WB_PROJECT_SCREEN", 35, true);
        screenname = elementchar("WB_SCREEN_NAME", 16, true);
        screenspecitemid = elementchar("WB_SCREEN_SPEC_ITEM", 38, true);
        screenspecitemname = elementchar("WB_SCREEN_SPEC_NAME", 32, true);
        screenitemtype = elementchar("WB_SCREEN_ITEM_TYPE", 24, false);
        projectmodel = elementchar("WB_PROJECT_MODEL", 35, true);
        modelname = elementchar("WB_MODEL_NAME", 24, true);
        modelitemid = elementchar("WB_MODEL_ITEM", 38, true);
        modelitemname = elementchar("WB_MODEL_ITEM_NAME", 24, true);
        modelitemlength = elementnumc("WB_MODEL_ITEM_LENGTH", 4);
        typeid = elementnumc("WB_TYPE_ID", 2);
        typetext = elementchar("WB_TYPE_TEXT", 16, false);
        linkid = elementchar("WB_LINK_ID", 50, true);
        linkname = new DummyElement("TASKS.NAME");
        command = new DummyElement("TASKS.COMMAND");
        
        /*
         * Tipos de dados
         */
        model = modelInstance(
                "WB_DATA_TYPE", "WBMDLITTP");
        datatype = model.key(
                "TYPE", "TYPID", typeid);
        model.item(
                "TEXT", "TYPTX", typetext);
        
        model.values(DataType.CHAR, "Caracter");
        model.values(DataType.DATE, "Data");
        model.values(DataType.DEC, "Decimal");
        model.values(DataType.NUMC, "Numerico inteiro");
        model.values(DataType.TIME, "Hora");
        model.values(DataType.BOOLEAN, "Booleano");
        model.values(DataType.BYTE, "Byte integer");
        model.values(DataType.INT, "Integer");
        model.values(DataType.LONG, "Long integer");
        model.values(DataType.SHORT, "Short integer");
        
        shd = searchHelpInstance("WB_SH_TYPE_ID", "WB_DATA_TYPE");
        shd.setExport("TYPE");
        shd.add("TYPE");
        shd.add("TEXT");
        
        /*
         * project header
         */
        model = tag("header", modelInstance(
                "WB_PROJECT_HEAD", "WBPRJCTHD"));
        project = model.key(
                "PROJECT_NAME", "PRJNM", projectname);
        model.item(
                "TEXT", "PRJTX", text);
        
        /*
         * project screens
         */
        model = tag("screens", modelInstance(
                "WB_PROJECT_SCREENS", "WBPRJCTSCR"));
        screen = model.key(
                "PROJECT_SCREEN", "SCRID", projectscreen);
        model.reference(
                "PROJECT", "PRJNM", project);
        model.item(
                "SCREEN_NAME", "SCRNM", screenname);
        
        /*
         * project screen spec items
         */
        model = tag("screen_spec_items", modelInstance(
                "WB_SCREEN_SPEC_ITEMS", "WBSCRSPECIT"));
        model.key(
                "ITEM_ID", "ITMID", screenspecitemid);
        model.reference(
                "PROJECT", "PRJNM", project);
        model.reference(
                "SCREEN", "SCRID", screen);
        model.item(
                "PARENT", "SITPA", screenspecitemname);
        model.item(
                "NAME", "SITNM", screenspecitemname);
        model.item(
                "TYPE", "ITTYP", screenitemtype);
        
        /*
         * model header
         */
        model = tag("models", modelInstance(
                "WB_PROJECT_MODELS", "WBPRJCTMDL"));
        modelid = model.key(
                "PROJECT_MODEL", "MDLID", projectmodel);
        model.reference(
                "PROJECT", "PRJNM", project);
        model.item(
                "MODEL_NAME", "MDLNM", modelname);
        
        /*
         * model item
         */
        model = tag("models_items", modelInstance(
                "WB_MODEL_ITEMS", "WBMODELIT"));
        model.key(
                "ITEM_ID", "ITMID", modelitemid);
        model.reference(
                "PROJECT", "PRJNM", project);
        model.reference(
                "MODEL", "MDLID", modelid);
        model.item(
                "NAME", "MDLNM", modelitemname);
        searchhelp(model.item(
                "TYPE", "MDLTY", datatype), "WB_SH_TYPE_ID");
        model.item(
                "LENGTH", "LNGTH", modelitemlength);
        
        /*
         * Links
         */
        model = tag("links", modelInstance(
                "WB_LINKS", "WBLINKS"));
        model.key(
                "LINK_ID", "LNKID", linkid);
        model.reference(
                "PROJECT", "PRJNM", project);
        model.item(
                "NAME", "LNKNM", linkname);
        model.item(
                "COMMAND", "CMMND", command);
        
        /*
         * project document
         */
        cmodel = cmodelInstance("WB_PROJECT");
        cmodel.header("header");
        cmodel.item("screen", "screens");
        cmodel.item("screen_spec_item", "screen_spec_items");
        cmodel.item("model", "models");
        cmodel.item("model_item", "models_items");
        cmodel.item("link", "links");
    }
}
