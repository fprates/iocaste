package org.iocaste.internal.renderer;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.internal.XMLElement;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.NodeList;

public class NodeListRenderer extends Renderer {

    public static final XMLElement render(NodeList nodelist, Config config) {
        String itelem;
        XMLElement nltag, itemnltag;
        List<XMLElement> tags;
        byte type = nodelist.getListType();
        
        switch (type) {
        case NodeList.DEFINITION:
            nltag = new XMLElement("dl");
            itelem = "dd";
            break;
            
        case NodeList.ORDERED:
            nltag = new XMLElement("ol");
            itelem = "li";
            break;
            
        default:
            nltag = new XMLElement("ul");
            itelem = "li";
            break;
        }
        
        nltag.add("id", nodelist.getName());
        nltag.add("class", nodelist.getStyleClass());
        
        addEvents(nltag, nodelist);

        if (type == NodeList.DEFINITION) {
            itemnltag = new XMLElement("dt");
            itemnltag.addInner(nodelist.getName());
            itemnltag.add("class", nodelist.getStyleClass(NodeList.NODE));
            nltag.addChild(itemnltag);
        }
        
        for (Element element : nodelist.getElements()) {
            tags = new ArrayList<XMLElement>();
            Renderer.renderElement(tags, element, config);
            
            itemnltag = new XMLElement(itelem);
            itemnltag.add("class", nodelist.getStyleClass(NodeList.ITEM));
            itemnltag.addChildren(tags);
            nltag.addChild(itemnltag);
        }
        
        return nltag;
    }
}
