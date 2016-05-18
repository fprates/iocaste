package org.iocaste.appbuilder.common;

import org.iocaste.appbuilder.common.cmodelviewer.EntityCustomPage;
import org.iocaste.appbuilder.common.cmodelviewer.EntityDisplayPage;
import org.iocaste.appbuilder.common.cmodelviewer.EntityPage;

public class AppBuilderLink {
    public String create, display, change, entity, cmodel, taskgroup;
    public String createview, create1view, displayview, display1view;
    public String editview, edit1view;
    public String number, numberseries, appname;
    public ViewSpec maintenancespec;
    public ViewConfig createselectconfig, displayselectconfig;
    public ViewConfig updateselectconfig, maintenanceconfig, displayconfig;
    public ViewInput maintenanceinput;
    public AbstractActionHandler save, validate, updateload, displayload;
    public AbstractActionHandler inputvalidate;
    public EntityPage entitypage;
    public EntityCustomPage custompage;
    public EntityDisplayPage displaypage;
}
