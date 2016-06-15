package org.iocaste.internal.renderer;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.NodeList;

public class NodeListRenderer extends Renderer {

    public static final XMLElement render(NodeList nodelist, Config config) {
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
        
        addEvents(nltag, nodelist);

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
