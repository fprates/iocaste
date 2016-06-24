package org.iocaste.workbench.install;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ComplexModelInstall;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DummyElement;

public class ProjectInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        DataElement projectname, text, screenname, modelname;
        DataElement screenspecitemid, screenspecitemname;
        DataElement modelitemid, modelitemname, command, modeltable, modelkey;
        DataElement screenitemtype, linkid, linkname, groupid, tableitemname;
        DataElement profile, dename, detype, desize, dedec, deupcase;
        ModelInstall model;
        ComplexModelInstall cmodel, models, screens;
        DocumentModelItem project, screen, modelid, dataelementid;

        projectname = elementchar("WB_PROJECT_NAME", 32, true);
        text = elementchar("WB_TEXT", 32, false);
        screenname = elementchar("WB_SCREEN_NAME", 16, true);
        screenspecitemid = elementchar("WB_SCREEN_SPEC_ITEM", 38, true);
        screenspecitemname = elementchar("WB_SCREEN_SPEC_NAME", 32, true);
        screenitemtype = elementchar("WB_SCREEN_ITEM_TYPE", 24, false);
        modelname = new DummyElement("MODEL.NAME");
        modeltable = new DummyElement("MODEL.TABLE");
        modelitemid = elementchar("WB_MODEL_ITEM", 38, true);
        modelitemname = elementchar("WB_MODEL_ITEM_NAME", 24, true);
        modelkey = elementbool("WB_BOOLEAN");
        tableitemname = new DummyElement("MODELITEM.FIELDNAME");
        linkid = elementchar("WB_LINK_ID", 50, true);
        linkname = new DummyElement("TASKS.NAME");
        command = new DummyElement("TASKS.COMMAND");
        groupid = new DummyElement("TASKS_GROUPS.NAME");
        profile = new DummyElement("USER_PROFILE.NAME");
        dename = new DummyElement("DATAELEMENT.NAME");
        detype = new DummyElement("DATAELEMENT.DECIMALS");
        desize = new DummyElement("DATAELEMENT.LENGTH");
        dedec = new DummyElement("DATAELEMENT.TYPE");
        deupcase = new DummyElement("DATAELEMENT.UPCASE");
        
        /*
         * project header
         */
        model = tag("header", modelInstance(
                "WB_PROJECT_HEAD", "WBPRJCTHD"));
        project = model.key(
                "PROJECT_NAME", "PRJNM", projectname);
        model.item(
                "PROFILE", "PROFL", profile);
        model.item(
                "TEXT", "PRJTX", text);
        
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
        model.item(
                "GROUP", "GRPID", groupid);
        
        /*
         * data elements
         */
        model = modelInstance(
                "WB_DATA_ELEMENTS", "WBDATAELEMENTS");
        dataelementid = model.key(
                "NAME", "DELNM", dename);
        model.reference(
                "PROJECT", "PRJNM", project);
        model.item(
                "TYPE", "DELTY", detype);
        model.item(
                "SIZE", "DELEN", desize);
        model.item(
                "DECIMALS", "DEDEC", dedec);
        model.item(
                "UPCASE", "DEUPC", deupcase);
        
        /*
         * model header
         */
        model = tag("model_head", modelInstance(
                "WB_MODEL_HEADER", "WBMODELHD"));
        modelid = model.key(
                "NAME", "MDLNM", modelname);
        model.reference(
                "PROJECT", "PRJNM", project);
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
                "PROJECT", "PRJNM", project);
        model.reference(
                "MODEL", "MDLID", modelid);
        model.item(
                "NAME", "ITMNM", modelitemname);
        model.item(
                "FIELD", "FLDNM", tableitemname);
        model.reference(
                "DATA_ELEMENT", "DTELM", dataelementid);
        model.item(
                "KEY", "MDKEY", modelkey);
        
        models = cmodelInstance("WB_MODELS");
        models.header("model_head");
        models.item("item", "model_item");
        
        /*
         * project screens
         */
        model = tag("screen_head", modelInstance(
                "WB_SCREEN_HEADER", "WBSCRHD"));
        screen = model.key(
                "NAME", "SCRNM", screenname);
        model.reference(
                "PROJECT", "PRJNM", project);
        
        /*
         * project screen spec items
         */
        model = tag("screen_spec", modelInstance(
                "WB_SCREEN_SPEC", "WBSCRSPEC"));
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
        
        screens = cmodelInstance("WB_SCREENS");
        screens.header("screen_head");
        screens.item("spec", "screen_spec");
        
        /*
         * project document
         */
        cmodel = cmodelInstance("WB_PROJECT");
        cmodel.header("header");
        cmodel.item("screen", screens);
        cmodel.item("link", "links");
        cmodel.item("models", models);
    }
}
