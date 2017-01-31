package org.iocaste.appbuilder.common.portal.tiles;

import java.util.LinkedHashSet;
import java.util.Set;

import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;

public class PortalTileItemData {
    public String key;
    public Set<String> show;
    
    public PortalTileItemData() {
        show = new LinkedHashSet<>();
    }
    
    public final static Set<String> showset(
            ExtendedContext extcontext, PortalTileItemData data) {
        Set<String> show;
        ExtendedObject object = extcontext.tilesobjectget("items");
        DocumentModel model = object.getModel();
        
        if ((data == null) || (data.show.size() == 0)) {
            show = new LinkedHashSet<>();
            for (DocumentModelItem item : model.getItens())
                show.add(item.getName());
        } else {
            show = data.show;
        }
        
        return show;
    }
}