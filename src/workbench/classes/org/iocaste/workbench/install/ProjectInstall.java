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
        DataElement projectname, projectscreen, text, screenname;
        DataElement screenspecitemid, screenspecitemname;
        ModelInstall model;
        ComplexModelInstall cmodel;
        DocumentModelItem project, screen;

        projectname = elementchar("WB_PROJECT_NAME", 32, true);
        text = elementchar("WB_TEXT", 32, false);
        projectscreen = elementchar("WB_PROJECT_SCREEN", 35, true);
        screenname = elementchar("WB_SCREEN_NAME", 16, true);
        screenspecitemid = elementchar("WB_SCREEN_SPEC_ITEM", 38, true);
        screenspecitemname = elementchar("WB_SCREEN_SPEC_NAME", 32, true);
        
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
         * project document
         */
        cmodel = cmodelInstance("WB_PROJECT");
        cmodel.header("header");
        cmodel.item("screen", "screens");
        cmodel.item("screen_spec_item", "screen_spec_items");
//        
//        element = new DataElement("WB_PROJECT_ID");
//        element.setType(DataType.NUMC);
//        element.setLength(3);
//        item = new DocumentModelItem("PROJECT_ID");
//        item.setTableFieldName("PRJID");
//        item.setDataElement(element);
//        model.add(item);
//        
//        item = new DocumentModelItem("SOURCE_OBJ");
//        item.setTableFieldName("SRCOB");
//        item.setDataElement(new DummyElement("TXTED_TEXTNAME"));
//        model.add(item);
//
//        sourceid = new DataElement("WB_SOURCE_ID");
//        sourceid.setType(DataType.NUMC);
//        sourceid.setLength(9);
//        item = new DocumentModelItem("SOURCE_ID");
//        item.setTableFieldName("SRCID");
//        item.setDataElement(sourceid);
//        model.add(item);
//
//        sourcename = new DataElement("WB_SOURCE_NAME");
//        sourcename.setType(DataType.CHAR);
//        sourcename.setLength(128);
//        sourcename.setUpcase(false);
//        item = new DocumentModelItem("ENTRY_CLASS");
//        item.setTableFieldName("ENTRY");
//        item.setDataElement(sourcename);
//        model.add(item);
//        
//        element = new DataElement("WB_SERVICE_CLASS");
//        element.setType(DataType.CHAR);
//        element.setLength(128);
//        element.setUpcase(false);
//        item = new DocumentModelItem("SERVICE_CLASS");
//        item.setTableFieldName("SRVCL");
//        item.setDataElement(element);
//        model.add(item);
//        
//        data.addNumberFactory("WBPROJECTID");
//        
//        /*
//         * Pacote
//         */
//        model = data.getModel("WB_PACKAGE", "WB_PACKAGE", null);
//        
//        element = new DataElement("WB_PACKAGE_ID");
//        element.setType(DataType.NUMC);
//        element.setLength(6);
//        packageid = new DocumentModelItem("PACKAGE_ID");
//        packageid.setTableFieldName("PKGID");
//        packageid.setDataElement(element);
//        model.add(new DocumentModelKey(packageid));
//        model.add(packageid);
//        
//        item = new DocumentModelItem("PROJECT_NAME");
//        item.setTableFieldName("PRJNM");
//        item.setDataElement(projectname.getDataElement());
//        item.setReference(projectname);
//        model.add(item);
//        
//        element = new DataElement("WB_PACKAGE_NAME");
//        element.setType(DataType.CHAR);
//        element.setLength(128);
//        element.setUpcase(false);
//        item = new DocumentModelItem("PACKAGE_NAME");
//        item.setTableFieldName("PKGNM");
//        item.setDataElement(element);
//        model.add(item);
//        
//        /*
//         * Fonte
//         */
//        model = data.getModel("WB_SOURCE", "WB_SOURCE", null);
//        
//        item = new DocumentModelItem("SOURCE_ID");
//        item.setTableFieldName("SRCID");
//        item.setDataElement(sourceid);
//        model.add(new DocumentModelKey(item));
//        model.add(item);
//        
//        item = new DocumentModelItem("PROJECT_NAME");
//        item.setTableFieldName("PRJNM");
//        item.setDataElement(projectname.getDataElement());
//        item.setReference(projectname);
//        model.add(item);
//
//        item = new DocumentModelItem("PACKAGE_ID");
//        item.setTableFieldName("PKGID");
//        item.setDataElement(packageid.getDataElement());
//        item.setReference(packageid);
//        model.add(item);
//
//        item = new DocumentModelItem("SOURCE_NAME");
//        item.setTableFieldName("SRCNM");
//        item.setDataElement(sourcename);
//        model.add(item);
        
    }
}
