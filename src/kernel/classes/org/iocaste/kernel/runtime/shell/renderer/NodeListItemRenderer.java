package org.iocaste.kernel.runtime.shell.renderer;

import java.util.Map;

import org.iocaste.kernel.runtime.shell.elements.NodeList;
import org.iocaste.kernel.runtime.shell.elements.NodeListItem;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;

public class NodeListItemRenderer extends AbstractElementRenderer<NodeListItem>
{
    public NodeListItemRenderer(Map<Const, Renderer<?>> renderers) {
        super(renderers, Const.NODE_LIST_ITEM);
    }

    @Override
    protected final XMLElement execute(NodeListItem item, Config config) {
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
        addAttributes(nltag, item);
        
        nltag.addChildren(renderElements(item.getElements(), config));
        return nltag;
    }
}
