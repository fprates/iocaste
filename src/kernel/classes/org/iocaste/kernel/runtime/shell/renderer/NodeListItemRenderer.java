package org.iocaste.kernel.runtime.shell.renderer;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.NodeList;
import org.iocaste.shell.common.NodeListItem;

public class NodeListItemRenderer extends AbstractElementRenderer<NodeListItem>
{
    private Map<Byte, String> tags;
    
    public NodeListItemRenderer(HtmlRenderer renderer) {
        super(renderer, Const.NODE_LIST_ITEM);
        tags = new HashMap<>();
        tags.put(NodeList.DEFINITION, "dd");
        tags.put(NodeList.ORDERED, "li");
    }

    @Override
    protected final XMLElement execute(NodeListItem item, Config config)
            throws Exception {
        XMLElement nltag;
        String style, text, tag;
        NodeList nodelist = (NodeList)item.getContainer();
        
        if (nodelist != null) {
            if ((tag = tags.get(nodelist.getListType())) != null)
                nltag = new XMLElement(tag);
            else
                nltag = new XMLElement("li");
        } else {
            nltag = new XMLElement(item.getTag());
        }

        nltag.add("id", item.getHtmlName());
        style = item.getStyleClass();
        if ((style == null) && (nodelist != null))
            style = nodelist.getItemsStyle();
        if (style != null)
            nltag.add("class", style);
        addAttributes(nltag, item);
        if ((text = item.getText()) != null)
            nltag.addInner(text);
        
        nltag.addChildren(renderElements(item.getElements(), config));
        return nltag;
    }
}
