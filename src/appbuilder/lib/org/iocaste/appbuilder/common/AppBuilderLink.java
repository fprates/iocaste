package org.iocaste.appbuilder.common;

import org.iocaste.appbuilder.common.cmodelviewer.Context;

public class AppBuilderLink {
    public String create, display, change, entity, cmodel, taskgroup;
    public String createview, create1view, displayview, display1view;
    public String editview, edit1view;
    public String number, appname;
    public ViewConfig createselectconfig, displayselectconfig;
    public ViewConfig updateselectconfig, maintenanceconfig;
    public AbstractActionHandler validate;
    public Context extcontext;
}
