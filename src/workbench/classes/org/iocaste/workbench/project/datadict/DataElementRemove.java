package org.iocaste.workbench.project.datadict;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;

public class DataElementRemove extends AbstractCommand {
    
    public DataElementRemove() {
        required("name");
    }
    
    @Override
    protected Object entry(PageBuilderContext context) {
        String name;
        ExtendedObject object;
        Query query;
        
        name = parameters.get("name");
        object = getObject("WB_DATA_ELEMENTS", name);
        if (object == null) {
            message(Const.ERROR, "undefined.data.element", name);
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
        print("data element %s removed.", name);
        return null;
    }

}
