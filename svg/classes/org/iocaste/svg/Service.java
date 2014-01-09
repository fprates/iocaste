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
        XMLElement xml = new XMLElement("svg");
        SVGData svgdata = message.get("data");
        Context context = new Context();
        
        for (SVGDataItem item : svgdata.get()) {
            switch (item.type) {
            case DIMENSION:
                xml.add("width", item.w);
                xml.add("height", item.h);
                break;
            case STYLE:
                context.style = item.style;
                break;
            default:
                xml.addChild(compileItem(item, context));
                break;
            }
        }
        
        return xml.toString();
    }
    
    private final XMLElement compileItem(SVGDataItem item, Context context) {
        XMLElement xmlitem;
        String href;
        
        switch (item.type) {
        case AXLINK:
            href = new StringBuilder("javascript:formSubmit('").
                    append(item.form.getHtmlName()).
                    append("', '").append(item.form.getAction()).
                    append("', '").append(item.action).
                    append("');").toString();
            xmlitem = new XMLElement("a");
            xmlitem.add("xlink:href", href);
            xmlitem.addInner(compileItem(item.dataitem, context).toString());
            return xmlitem;
        case CIRCLE:
            xmlitem = new XMLElement("circle");
            xmlitem.add("cx", item.x1);
            xmlitem.add("cy", item.y1);
            xmlitem.add("r", item.r);
            xmlitem.add("style", context.style);
            return xmlitem;
        case LINE:
            xmlitem = new XMLElement("line");
            xmlitem.add("x1", item.x1);
            xmlitem.add("y1", item.y1);
            xmlitem.add("x2", item.x2);
            xmlitem.add("y2", item.y2);
            xmlitem.add("style", context.style);
            return xmlitem;
        case RECTANGLE:
            xmlitem = new XMLElement("rect");
            xmlitem.add("x", item.x1);
            xmlitem.add("y", item.y1);
            xmlitem.add("width", item.w);
            xmlitem.add("height", item.h);
            xmlitem.add("style", context.style);
            return xmlitem;
        case TEXT:
            xmlitem = new XMLElement("text");
            xmlitem.add("x", item.x1);
            xmlitem.add("y", item.y1);
            xmlitem.add("style", context.style);
            xmlitem.addInner(item.text);
            return xmlitem;
        default:
            return null;
        }
    }
}

class Context {
    public String style;
}