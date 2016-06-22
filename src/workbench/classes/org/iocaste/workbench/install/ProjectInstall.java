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
        DataElement projectname, projectscreen, text, screenname, modelname;
        DataElement screenspecitemid, screenspecitemname, projectmodel;
        DataElement modelitemid, modelitemname, command, modeltable, modelkey;
        DataElement screenitemtype, linkid, linkname, groupid, tableitemname;
        DataElement profile, deid, dename, detype, desize, dedec, deupcase;
        ModelInstall model;
        ComplexModelInstall cmodel;
        DocumentModelItem project, screen, modelid, dataelementid;

        projectname = elementchar("WB_PROJECT_NAME", 32, true);
        text = elementchar("WB_TEXT", 32, false);
        projectscreen = elementchar("WB_PROJECT_SCREEN", 35, true);
        screenname = elementchar("WB_SCREEN_NAME", 16, true);
        screenspecitemid = elementchar("WB_SCREEN_SPEC_ITEM", 38, true);
        screenspecitemname = elementchar("WB_SCREEN_SPEC_NAME", 32, true);
        screenitemtype = elementchar("WB_SCREEN_ITEM_TYPE", 24, false);
        projectmodel = elementchar("WB_PROJECT_MODEL", 35, true);
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
        deid = elementchar("WB_DEL_ID", 80, true);
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
         * data elements
         */
        model = tag("data_elements", modelInstance(
                "WB_DATA_ELEMENTS", "WBDATAELEMENTS"));
        dataelementid = model.key(
                "DE_ID", "DELID", deid);
        model.reference(
                "PROJECT", "PRJNM", project);
        model.item(
                "NAME", "DELNM", dename);
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
        model = tag("models", modelInstance(
                "WB_MODEL_HEADER", "WBMODELHD"));
        modelid = model.key(
                "MODEL_ID", "MDLID", projectmodel);
        model.reference(
                "PROJECT", "PRJNM", project);
        model.item(
                "NAME", "MDLNM", modelname);
        model.item(
                "TABLE", "MDLTB", modeltable);
        
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
                "NAME", "ITMNM", modelitemname);
        model.item(
                "FIELD", "FLDNM", tableitemname);
        model.reference(
                "DATA_ELEMENT", "DTELM", dataelementid);
        model.item(
                "KEY", "MDKEY", modelkey);
        
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
         * project document
         */
        cmodel = cmodelInstance("WB_PROJECT");
        cmodel.header("header");
        cmodel.item("screen", "screens");
        cmodel.item("screen_spec_item", "screen_spec_items");
        cmodel.item("dataelement", "data_elements");
        cmodel.item("model", "models");
        cmodel.item("link", "links");
        
        cmodel = cmodelInstance("WB_MODELS");
        cmodel.header("models");
        cmodel.item("item", "models_items");
    }
}
