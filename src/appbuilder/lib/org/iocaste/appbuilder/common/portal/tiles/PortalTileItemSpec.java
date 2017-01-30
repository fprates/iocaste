package org.iocaste.appbuilder.common.portal.tiles;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;

public class PortalTileItemSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        String name, nodeitem;
        ExtendedObject object = getExtendedContext().tilesobjectget("items");
        DocumentModel model = object.getModel();
        
        link(parent, "item");
        nodelist("item", "node_item");
        for (DocumentModelItem item : model.getItens()) {
            name = item.getName();
            nodeitem = name.concat("_node");
            nodelistitem("node_item", nodeitem);
            text(nodeitem, name);
        }   
    }
}
