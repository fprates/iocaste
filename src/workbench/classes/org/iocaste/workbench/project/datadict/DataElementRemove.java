package org.iocaste.workbench.project.datadict;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.ActionContext;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.viewer.ViewerItemUpdate;

public class DataElementRemove extends AbstractCommand {
    
    public DataElementRemove(Context extcontext) {
        super("data-element-remove", extcontext);
        ActionContext actionctx;
        
        required("name", "NAME");
        actionctx = getActionContext();
        actionctx.updateviewer =
                new ViewerItemUpdate(extcontext, "data_elements_items");
    }
    
    @Override
    protected Object entry(PageBuilderContext context) {
        String name;
        ExtendedObject object;
        Query query;
        
        name = getst("name");
        object = getObject("WB_DATA_ELEMENTS", name);
        if (object == null) {
            message(Const.ERROR, "invalid.dataelement", name);
            return null;
        }
        
        query = new Query();
        query.setModel("WB_MODEL_ITEMS");
        query.setMaxResults(1);
        query.andEqual("DATA_ELEMENT", name);
        if (select(query) != null) {
            message(Const.ERROR, "dataelement.still.assigned");
            return null;
        }
        delete(object);
        message(Const.STATUS, "dataelement.removed");
        return null;
    }

}
