package org.iocaste.workbench.install;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ComplexModelInstall;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.ComplexModelItem;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModelItem;

public class ViewInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) {
        ModelInstall model;
        ComplexModelInstall cmodel;
        ComplexModelItem cmodelitem;
        DataElement screenconfigitemid, screenconfigname, screenconfigvalue;
        DataElement screenconfigtype, screenactionname, screenactionclass;
        DataElement screenactionid, screenactiontype, screenspecitemid;
        DataElement screenspecitemname, screenitemtype, screenname;
        DocumentModelItem screen, speckey;
        
        screenname = elementchar("WB_SCREEN_NAME", 16, false);
        screenspecitemid = elementchar("WB_SCREEN_SPEC_ITEM", 19, false);
        screenspecitemname = elementchar("WB_SCREEN_SPEC_NAME", 32, false);
        screenitemtype = elementchar("WB_SCREEN_ITEM_TYPE", 24, false);
        screenconfigitemid = elementchar("WB_SCREEN_CFG_ID", 20, false);
        screenconfigname = elementchar("WB_SCREEN_CFG_NAME", 24, false);
        screenconfigvalue = elementchar("WB_SCREEN_CFG_VALUE", 255, false);
        screenconfigtype = elementnumc("WB_SCREEN_CFG_TYPE", 1);
        screenactionid = elementchar("WB_SCREEN_ACTION_ID", 19, false);
        screenactionname = elementchar("WB_SCREEN_ACTION_NAME", 40, false);
        screenactionclass = elementchar("WB_SCREEN_ACTION_CLASS", 255, false);
        screenactiontype = elementnumc("WB_SCREEN_ACTION_TYPE", 1);
        
        /*
         * project screens
         */
        model = tag("screen_head", modelInstance(
                "WB_SCREEN_HEADER", "WBSCRHD"));
        screen = model.key(
                "NAME", "SCRNM", screenname);
        model.reference(
                "PROJECT", "PRJNM", getItem("projectkey"));
        
        /*
         * project screen spec items
         */
        model = tag("screen_spec", modelInstance(
                "WB_SCREEN_SPEC", "WBSCRSPEC"));
        speckey = model.key(
                "ITEM_ID", "ITMID", screenspecitemid);
        model.reference(
                "PROJECT", "PRJNM", getItem("projectkey"));
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
                "PROJECT", "PRJNM", getItem("projectkey"));
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
                "PROJECT", "PRJNM", getItem("projectkey"));
        model.reference(
                "SCREEN", "SCRID", screen);
        model.item(
                "NAME", "ACTNM", screenactionname);
        model.item(
                "CLASS", "CLASS", screenactionclass);
        model.item(
                "TYPE", "ACTTY", screenactiontype);
        
        cmodel = tag("screens", cmodelInstance("WB_SCREENS"));
        cmodel.header("screen_head");
        cmodelitem = cmodel.item("spec", "screen_spec");
        cmodelitem.index = "NAME";
        cmodelitem.keyformat = "%03d";
        cmodel.item("config", "screen_config").keyformat = "%04d";
        cmodel.item("action", "screen_action").keyformat = "%03d";
    }
    
}