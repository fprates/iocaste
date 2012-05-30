package org.iocaste.shell.renderer;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.shell.XMLElement;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.NodeList;

public class NodeListRenderer extends Renderer {

    public static final XMLElement render(NodeList nodelist, Config config) {
        String itelem;
        XMLElement nltag, itemnltag;
        List<XMLElement> tags;
        
        switch (nodelist.getListType()) {
        case NodeList.DEFINITION:
            nltag = new XMLElement("dl");
            itelem = "dt";
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
        
        for (Element element : nodelist.getElements()) {
            tags = new ArrayList<XMLElement>();
            Renderer.renderElement(tags, element, config);
            
            itemnltag = new XMLElement(itelem);
            itemnltag.addChildren(tags);
            nltag.addChild(itemnltag);
        }
        
        return nltag;
    }
}
