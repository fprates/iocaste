package org.iocaste.packagetool;

import java.util.List;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;

public class DetailPackage extends AbstractActionHandler {
    private String table;
    
    public DetailPackage(String table) {
        this.table = table;
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        List<ExtendedObject> items;
        Context extcontext;
        
        items = tableselectedget(table);
        if (items == null)
            return;
        
        extcontext = getExtendedContext();
        for (ExtendedObject item : items) {
            extcontext.exception = extcontext.exceptions.
                    get(item.getst("NAME"));
            break;
        }
        
        redirect("detail");
    }

}
