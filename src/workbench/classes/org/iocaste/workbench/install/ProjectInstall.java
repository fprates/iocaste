package org.iocaste.workbench.install;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ComplexModelInstall;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModelItem;

public class ProjectInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        DataElement projectname, projectscreen, text, screenname, modelname;
        DataElement screenspecitemid, screenspecitemname, projectmodel;
        DataElement modelitemid, modelitemname;
        ModelInstall model;
        ComplexModelInstall cmodel;
        DocumentModelItem project, screen, modelid;

        projectname = elementchar("WB_PROJECT_NAME", 32, true);
        text = elementchar("WB_TEXT", 32, false);
        projectscreen = elementchar("WB_PROJECT_SCREEN", 35, true);
        screenname = elementchar("WB_SCREEN_NAME", 16, true);
        screenspecitemid = elementchar("WB_SCREEN_SPEC_ITEM", 38, true);
        screenspecitemname = elementchar("WB_SCREEN_SPEC_NAME", 32, true);
        projectmodel = elementchar("WB_PROJECT_MODEL", 35, true);
        modelname = elementchar("WB_MODEL_NAME", 24, true);
        modelitemid = elementchar("WB_MODEL_ITEM", 38, true);
        modelitemname = elementchar("WB_MODEL_ITEM_NAME", 24, true);
        
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
                "NAME", "SITNM", screenspecitemname);
        
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
        
        /*
         * project document
         */
        cmodel = cmodelInstance("WB_PROJECT");
        cmodel.header("header");
        cmodel.item("screen", "screens");
        cmodel.item("screen_spec_item", "screen_spec_items");
        cmodel.item("model", "models");
        cmodel.item("model_item", "models_items");
    }
}
