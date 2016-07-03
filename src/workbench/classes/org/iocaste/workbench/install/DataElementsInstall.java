package org.iocaste.workbench.install;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DummyElement;

public class DataElementsInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) {
        ModelInstall model;
        DataElement dename, detype, desize, dedec, deupcase;
        
        dename = new DummyElement("DATAELEMENT.NAME");
        detype = new DummyElement("DATAELEMENT.DECIMALS");
        desize = new DummyElement("DATAELEMENT.LENGTH");
        dedec = new DummyElement("DATAELEMENT.TYPE");
        deupcase = new DummyElement("DATAELEMENT.UPCASE");
        /*
         * data elements
         */
        model = modelInstance(
                "WB_DATA_ELEMENTS", "WBDATAELEMENTS");
        tag("dataelementkey", model.key(
                "NAME", "DELNM", dename));
        model.reference(
                "PROJECT", "PRJNM", getItem("projectkey"));
        model.item(
                "TYPE", "DELTY", detype);
        model.item(
                "SIZE", "DELEN", desize);
        model.item(
                "DECIMALS", "DEDEC", dedec);
        model.item(
                "UPCASE", "DEUPC", deupcase);
    }
    
}

