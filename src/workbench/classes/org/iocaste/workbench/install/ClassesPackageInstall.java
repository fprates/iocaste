package org.iocaste.workbench.install;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ComplexModelInstall;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.ComplexModelItem;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModelItem;

public class ClassesPackageInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) {
        ComplexModelInstall cmodel;
        ComplexModelItem cmodelitem;
        ModelInstall model;
        DocumentModelItem packagekey;
        DataElement classid, classname, classfullname, packagename;
        
        
        packagename = elementchar("WB_JAVA_PACKAGE_NAME", 120, false);
        classid = elementchar("WB_JAVA_CLASS_ID", 125, false);
        classname = elementchar("WB_JAVA_CLASS_NAME", 120, false);
        classfullname = elementchar("WB_JAVA_CLASS_FULL", 250, false);
        
        /*
         * java package
         */
        model = tag("packages", modelInstance(
                "WB_JAVA_PACKAGE", "WBJVPKG"));
        packagekey = model.key(
                "PACKAGE", "PKGNM", packagename);
        model.reference(
                "PROJECT", "PRJNM", getItem("projectkey"));
        
        /*
         * java class
         */
        model = tag("classes", modelInstance(
                "WB_JAVA_CLASS", "WBJVCLSS"));
        model.key(
                "CLASS_ID", "CLSID", classid);
        model.reference(
                "PROJECT", "PRJNM", getItem("projectkey"));
        model.reference(
                "PACKAGE", "PKGNM", packagekey);
        model.item(
                "NAME", "CLSNM", classname);
        model.item(
                "FULL_NAME", "FLLNM", classfullname);
        
        cmodel = tag("classes", cmodelInstance("WB_JAVA_PACKAGES"));
        cmodel.header("packages");
        cmodelitem = cmodel.item("class", "classes");
        cmodelitem.keyformat = "%05d";
        cmodelitem.index = "FULL_NAME";
    }
    
}