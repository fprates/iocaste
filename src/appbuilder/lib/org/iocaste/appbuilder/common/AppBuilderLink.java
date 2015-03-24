package org.iocaste.appbuilder.common;

public class AppBuilderLink {
    public String create, display, change, entity, cmodel, taskgroup;
    public String createview, create1view, displayview, display1view;
    public String editview, edit1view;
    public String number, numberseries, appname;
    public AbstractViewSpec maintenancespec;
    public ViewConfig createselectconfig, displayselectconfig;
    public ViewConfig updateselectconfig, maintenanceconfig, displayconfig;
    public AbstractViewInput maintenanceinput;
    public AbstractActionHandler save, validate, updateload, displayload;
}
