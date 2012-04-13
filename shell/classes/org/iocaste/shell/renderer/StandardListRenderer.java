package org.iocaste.shell.renderer;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.shell.XMLElement;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.StandardList;

public class StandardListRenderer extends Renderer {

    public static final XMLElement render(StandardList list, Config config) {
        List<XMLElement> tags;
        XMLElement itemtag, listtag = new XMLElement("ul");
        
        for (Element element : list.getElements()) {
            itemtag = new XMLElement("li");
            tags = new ArrayList<XMLElement>();
            Renderer.renderElement(tags, element, config);
            itemtag.addChildren(tags);
            
            listtag.addChild(itemtag);
        }
        
        return listtag;
    }
}
