package org.iocaste.appbuilder.common.portal.tiles;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Link;

public class PortalTileItemConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        Link link;
        ExtendedObject object = getExtendedContext().tilesobjectget("items");
        DocumentModel model = object.getModel();
        boolean first = true;
        
        getElement("node_item").setStyleClass("portal_tile_frame");
        link = getElement("item");
        link.setAction("pick");
        
        for (DocumentModelItem item : model.getItens()) {
            getElement(item.getName()).setStyleClass(
                    (first)? "portal_tile_key" : "portal_tile_text");
            first = false;
        }
    }
    
}