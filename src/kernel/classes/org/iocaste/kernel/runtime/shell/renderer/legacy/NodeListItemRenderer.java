package org.iocaste.kernel.runtime.shell.renderer.legacy;

import org.iocaste.kernel.runtime.shell.renderer.AbstractElementRenderer;
import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.NodeList;
import org.iocaste.shell.common.NodeListItem;

public class NodeListItemRenderer extends AbstractElementRenderer<NodeListItem> {

    public NodeListItemRenderer(HtmlRenderer renderers) {
        super(renderers, Const.NODE_LIST_ITEM);
    }

    @Override
    protected final XMLElement execute(NodeListItem item, Config config)
            throws Exception {
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