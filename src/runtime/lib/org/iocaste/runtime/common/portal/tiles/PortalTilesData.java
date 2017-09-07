package org.iocaste.runtime.common.portal.tiles;

import java.util.LinkedHashSet;
import java.util.Set;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.runtime.common.application.Context;

public class PortalTilesData {
    public String key, action, highlight;
    public Set<String> show;
    
    public PortalTilesData() {
        show = new LinkedHashSet<>();
    }
    
    public final static Set<String> showset(
            Context context, PortalTilesData data) {
        Set<String> show;
        ExtendedObject object = context.getPage().instance("items").objects.
                get(0);
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