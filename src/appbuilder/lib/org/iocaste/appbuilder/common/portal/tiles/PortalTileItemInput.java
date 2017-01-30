package org.iocaste.appbuilder.common.portal.tiles;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.StandardViewInput;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;

public class PortalTileItemInput extends StandardViewInput {
    
    @Override
    public void execute(PageBuilderContext context) {
        String name = null;
        ExtendedObject object = getExtendedContext().tilesobjectget("items");
        
        for (DocumentModelItem item : object.getModel().getItens()) {
            if (name == null)
                linkadd("item", object, name = item.getName());
            else
                name = item.getName();
            textset(name, object.get(name).toString());
        }
        
        textset("item", "");
    }
}