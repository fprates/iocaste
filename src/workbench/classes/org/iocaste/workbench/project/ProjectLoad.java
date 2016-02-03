package org.iocaste.workbench.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.WorkbenchContext;

public class ProjectLoad extends AbstractActionHandler {
    private String redirect;

    public ProjectLoad(String redirect) {
        this.redirect = redirect;
    }
    
    @Override
    protected final void execute(PageBuilderContext context) {
        String ttname, model;
        Map<String, List<ExtendedObject>> models;
        List<ExtendedObject> items;
        WorkbenchContext extcontext = getExtendedContext();
        
        extcontext.id = getdfkey("head");
        extcontext.ns = getdfns("head");
        extcontext.document = getDocument(
                extcontext.link.cmodel, extcontext.ns, extcontext.id);
        if (extcontext.document == null) {
            managerMessage(
                    extcontext.link.cmodel, Const.ERROR, Manager.EINVALID);
            return;
        }
        
        extcontext.pageInstance(redirect);
        for (String key : new String[] {"model"}) {
            ttname = key.concat("_table");
            extcontext.tableInstance(redirect, ttname);
            extcontext.set(redirect, ttname, extcontext.document.getItems(key));
        }
        
        models = new HashMap<>();
        for (ExtendedObject object : extcontext.document.getItems("model_item"))
        {
            model = object.getst("MODEL");
            items = models.get(model);
            if (items == null) {
                items = new ArrayList<>();
                models.put(model, items);
            }
            items.add(object);
        }
        
        for (String key : models.keySet())
            extcontext.models.put(
                    key, models.get(key).toArray(new ExtendedObject[0]));
        
        init(redirect, extcontext);
        redirect(redirect);
    }

}
