package org.iocaste.appbuilder.common;

import org.iocaste.appbuilder.common.cmodelviewer.EntityCustomPage;
import org.iocaste.appbuilder.common.cmodelviewer.EntityDisplayPage;
import org.iocaste.appbuilder.common.cmodelviewer.EntityPage;
import org.iocaste.appbuilder.common.panel.AbstractPanelSpec;

public class AppBuilderLink {
    public String create, display, change, entity, cmodel, taskgroup;
    public String createview, create1view, displayview, display1view;
    public String editview, edit1view;
    public String number, numberseries, appname;
    public AbstractPanelSpec maintenancespec;
    public ViewConfig createselectconfig, displayselectconfig;
    public ViewConfig updateselectconfig, maintenanceconfig, displayconfig;
    public AbstractViewInput maintenanceinput;
    public AbstractActionHandler save, validate, updateload, displayload;
    public EntityPage entitypage;
    public EntityCustomPage custompage;
    public EntityDisplayPage displaypage;
}
