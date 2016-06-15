package org.iocaste.internal.renderer;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.NodeList;
import org.iocaste.shell.common.NodeListItem;

public class NodeListItemRenderer extends Renderer {

    public static final XMLElement render(NodeListItem item, Config config) {
        XMLElement nltag;
        String style;
        NodeList nodelist = (NodeList)item.getContainer();
        byte type = nodelist.getListType();
        
        switch (type) {
        case NodeList.DEFINITION:
            nltag = new XMLElement("dd");
            break;
            
        case NodeList.ORDERED:
            nltag = new XMLElement("li");
            break;
            
        default:
            nltag = new XMLElement("li");
            break;
        }

        nltag.add("id", item.getHtmlName());
        style = item.getStyleClass();
        if (style == null)
            style = nodelist.getItemsStyle();
        if (style != null)
            nltag.add("class", style);
        addEvents(nltag, item);
        
        nltag.addChildren(renderElements(item.getElements(), config));
        return nltag;
    }
}
