package org.iocaste.workbench.install;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ComplexModelInstall;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.ComplexModelItem;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DummyElement;

public class ProjectInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        DataElement projectname, text, screenname, modelname;
        DataElement screenspecitemid, screenspecitemname, classfullname;
        DataElement modelitemid, modelitemname, command, modeltable, modelkey;
        DataElement screenitemtype, linkid, linkname, groupid, tableitemname;
        DataElement profile, dename, detype, desize, dedec, deupcase;
        DataElement screenconfigitemid, screenconfigname, screenconfigvalue;
        DataElement screenconfigtype, screenactionname, screenactionclass;
        DataElement screenactionid, packagename, classid, classname;
        DataElement screenactiontype;
        ModelInstall model;
        ComplexModelItem cmodelitem;
        ComplexModelInstall cmodel, models, screens, classes;
        DocumentModelItem project, screen, modelid, dataelementid, speckey;
        DocumentModelItem packagekey;

        linkname = new DummyElement("TASKS.NAME");
        command = new DummyElement("TASKS.COMMAND");
        groupid = new DummyElement("TASKS_GROUPS.NAME");
        profile = new DummyElement("USER_PROFILE.NAME");
        dename = new DummyElement("DATAELEMENT.NAME");
        detype = new DummyElement("DATAELEMENT.DECIMALS");
        desize = new DummyElement("DATAELEMENT.LENGTH");
        dedec = new DummyElement("DATAELEMENT.TYPE");
        deupcase = new DummyElement("DATAELEMENT.UPCASE");

        modelname = new DummyElement("MODEL.NAME");
        modeltable = new DummyElement("MODEL.TABLE");
        tableitemname = new DummyElement("MODELITEM.FIELDNAME");
        modelitemid = elementchar("WB_MODEL_ITEM", 38, true);
        modelitemname = elementchar("WB_MODEL_ITEM_NAME", 24, true);
        modelkey = elementbool("WB_BOOLEAN");
        
        projectname = elementchar("WB_PROJECT_NAME", 32, true);
        text = elementchar("WB_TEXT", 32, false);
        linkid = elementchar("WB_LINK_ID", 50, true);

        screenname = elementchar("WB_SCREEN_NAME", 16, true);
        screenspecitemid = elementchar("WB_SCREEN_SPEC_ITEM", 19, true);
        screenspecitemname = elementchar("WB_SCREEN_SPEC_NAME", 32, true);
        screenitemtype = elementchar("WB_SCREEN_ITEM_TYPE", 24, false);
        screenconfigitemid = elementchar("WB_SCREEN_CFG_ID", 20, true);
        screenconfigname = elementchar("WB_SCREEN_CFG_NAME", 24, false);
        screenconfigvalue = elementchar("WB_SCREEN_CFG_VALUE", 255, false);
        screenconfigtype = elementnumc("WB_SCREEN_CFG_TYPE", 1);
        screenactionid = elementchar("WB_SCREEN_ACTION_ID", 19, false);
        screenactionname = elementchar("WB_SCREEN_ACTION_NAME", 40, false);
        screenactionclass = elementchar("WB_SCREEN_ACTION_CLASS", 255, false);
        screenactiontype = elementnumc("WB_SCREEN_ACTION_TYPE", 1);
        
        packagename = elementchar("WB_JAVA_PACKAGE_NAME", 120, false);
        classid = elementchar("WB_JAVA_CLASS_ID", 125, false);
        classname = elementchar("WB_JAVA_CLASS_NAME", 120, false);
        classfullname = elementchar("WB_JAVA_CLASS_FULL", 250, false);
        
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
        models.item("item", "model_item").index = "NAME";
        
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
        speckey = model.key(
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
         * project screen config items
         */
        model = tag("screen_config", modelInstance(
                "WB_SCREEN_CONFIG", "WBSCRCFG"));
        model.key(
                "CONFIG_ID", "CFGID", screenconfigitemid);
        model.reference(
                "PROJECT", "PRJNM", project);
        model.reference(
                "SCREEN", "SCRID", screen);
        model.reference(
                "SPEC", "ITMID", speckey);
        model.item(
                "NAME", "CFGNM", screenconfigname);
        model.item(
                "VALUE", "CFGVL", screenconfigvalue);
        model.item(
                "TYPE", "CFGTY", screenconfigtype);
        
        /*
         * project screen actions
         */
        model = tag("screen_action", modelInstance(
                "WB_SCREEN_ACTION", "WBSCRACT"));
        model.key(
                "ACTION_ID", "ACTID", screenactionid);
        model.reference(
                "PROJECT", "PRJNM", project);
        model.reference(
                "SCREEN", "SCRID", screen);
        model.item(
                "NAME", "ACTNM", screenactionname);
        model.item(
                "CLASS", "CLASS", screenactionclass);
        model.item(
                "TYPE", "ACTTY", screenactiontype);
        
        screens = cmodelInstance("WB_SCREENS");
        screens.header("screen_head");
        cmodelitem = screens.item("spec", "screen_spec");
        cmodelitem.index = "NAME";
        cmodelitem.keyformat = "%03d";
        screens.item("config", "screen_config").keyformat = "%04d";
        screens.item("action", "screen_action").keyformat = "%03d";
        
        /*
         * java package
         */
        model = tag("packages", modelInstance(
                "WB_JAVA_PACKAGE", "WBJVPKG"));
        packagekey = model.key(
                "PACKAGE", "PKGNM", packagename);
        model.reference(
                "PROJECT", "PRJNM", project);
        
        /*
         * java class
         */
        model = tag("classes", modelInstance(
                "WB_JAVA_CLASS", "WBJVCLSS"));
        model.key(
                "CLASS_ID", "CLSID", classid);
        model.reference(
                "PROJECT", "PRJNM", project);
        model.reference(
                "PACKAGE", "PKGNM", packagekey);
        model.item(
                "NAME", "CLSNM", classname);
        model.item(
                "FULL_NAME", "FLLNM", classfullname);
        
        classes = cmodelInstance("WB_JAVA_PACKAGES");
        classes.header("packages");
        cmodelitem = classes.item("class", "classes");
        cmodelitem.keyformat = "%05d";
        cmodelitem.index = "FULL_NAME";
        
        /*
         * project document
         */
        cmodel = cmodelInstance("WB_PROJECT");
        cmodel.header("header");
        cmodel.item("screen", screens);
        cmodel.item("link", "links").index = "NAME";
        cmodel.item("model", models);
        cmodel.item("class", classes);
    }
}
