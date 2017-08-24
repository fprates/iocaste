package org.iocaste.runtime.common.managedview;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.runtime.common.application.AbstractActionHandler;
import org.iocaste.runtime.common.page.AbstractPage;

public class ManagedViewContext {
    public static final String CREATE = "create";
    public static final String DISPLAY = "display";
    public static final String EDIT = "edit";
    public String create, display, change, entity, cmodel, taskgroup;
    public String createview, create1view, displayview, display1view;
    public String editview, edit1view;
    public String number, numberseries, appname, action, entitypagename;
    public String redirect;
    public AbstractPage maintenancepage, createselectpage;
    public AbstractPage updateselectpage, displayselectpage;
    public AbstractActionHandler<?> save, updateload, displayload;
    public AbstractActionHandler<?> createvalidate, inputvalidate;
    public Map<String, String> models;
    public Object id, ns;
    public ComplexDocument document;
    public boolean nshidden;
    
    public ManagedViewContext(String entity) {
        this.entity = entity;
        createview = entity.concat(CREATE);
        create1view = createview.concat("1");
        editview = entity.concat(EDIT);
        edit1view = editview.concat("1");
        displayview = entity.concat(DISPLAY);
        display1view = displayview.concat("1");
        models = new HashMap<>();
    }
}
