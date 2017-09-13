package org.iocaste.kernel.runtime.shell.renderer;

import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.tooldata.NodeList;

public class NodeListRenderer extends AbstractElementRenderer<NodeList> {

    public NodeListRenderer(HtmlRenderer renderer) {
        super(renderer, Const.NODE_LIST);
    }

    @Override
    protected final XMLElement execute(NodeList nodelist, Config config) {
        XMLElement nltag, itemnltag;
        byte type = nodelist.getListType();
        
        switch (type) {
        case NodeList.DEFINITION:
            nltag = new XMLElement("dl");
            break;
            
        case NodeList.ORDERED:
            nltag = new XMLElement("ol");
            break;
            
        default:
            nltag = new XMLElement("ul");
            break;
        }
        
        nltag.add("id", nodelist.getName());
        nltag.add("class", nodelist.getStyleClass());
        
        addAttributes(nltag, nodelist);

        if (type == NodeList.DEFINITION) {
            itemnltag = new XMLElement("dt");
            itemnltag.addInner(nodelist.getName());
            itemnltag.add("class", nodelist.getStyleClass());
            nltag.addChild(itemnltag);
        }
        
        nltag.addChildren(renderElements(nodelist.getElements(), config));
        return nltag;
    }
}
