package org.iocaste.appbuilder.common;

import org.iocaste.appbuilder.common.cmodelviewer.AbstractEntityCustomPage;
import org.iocaste.appbuilder.common.cmodelviewer.AbstractEntityDisplayPage;
import org.iocaste.appbuilder.common.cmodelviewer.AbstractEntityPage;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

public class AppBuilderLink {
    public String create, display, change, entity, cmodel, taskgroup;
    public String createview, create1view, displayview, display1view;
    public String editview, edit1view;
    public String number, numberseries, appname;
    public AbstractPanelPage maintenancepage, createselectpage;
    public AbstractPanelPage updateselectpage, displayselectpage;
    public AbstractActionHandler save, validate, updateload, displayload;
    public AbstractActionHandler inputvalidate;
    public AbstractEntityPage entitypage;
    public AbstractEntityCustomPage custompage;
    public AbstractEntityDisplayPage displaypage;
}
