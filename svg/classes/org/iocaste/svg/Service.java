package org.iocaste.svg;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.svg.common.SVGData;
import org.iocaste.svg.common.SVGDataItem;

public class Service extends AbstractFunction {

    public Service() {
        export("compile", "compile");
    }
    
    public final String compile(Message message) {
        XMLElement xmlitem;
        String style = null;
        XMLElement xml = new XMLElement("svg");
        SVGData svgdata = message.get("data");
        
        for (SVGDataItem item : svgdata.get()) {
            switch (item.type) {
            case CIRCLE:
                xmlitem = new XMLElement("circle");
                xmlitem.add("cx", item.cx);
                xmlitem.add("cy", item.cy);
                xmlitem.add("r", item.r);
                xmlitem.add("style", style);
                xml.addChild(xmlitem);
                break;
            case DIMENSION:
                xml.add("width", item.w);
                xml.add("height", item.h);
                break;
            case LINE:
                xmlitem = new XMLElement("line");
                xmlitem.add("x1", item.x1);
                xmlitem.add("y1", item.y1);
                xmlitem.add("x2", item.x2);
                xmlitem.add("y2", item.y2);
                xmlitem.add("style", style);
                xml.addChild(xmlitem);
                break;
            case RECTANGLE:
                xmlitem = new XMLElement("rect");
                xmlitem.add("x", item.x1);
                xmlitem.add("y", item.y1);
                xmlitem.add("width", item.w);
                xmlitem.add("height", item.h);
                xmlitem.add("style", style);
                xml.addChild(xmlitem);
                break;
            case STYLE:
                style = item.style;
                break;
            }
        }
        
        return xml.toString();
    }
}
