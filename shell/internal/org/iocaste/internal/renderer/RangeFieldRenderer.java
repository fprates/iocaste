package org.iocaste.internal.renderer;

import org.iocaste.documents.common.ValueRange;
import org.iocaste.documents.common.ValueRangeItem;
import org.iocaste.internal.XMLElement;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.RangeField;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.TextField;

public class RangeFieldRenderer extends Renderer {

    public static final XMLElement render(RangeField rfield, Config config) {
        ValueRangeItem value;
        TextField input;
        Element[] elements = new Element[3];
        XMLElement rfieldtag = new XMLElement("div");
        
        elements[0] = new TextField(null, "low");
        elements[0].setHtmlName(rfield.getLowHtmlName());
        elements[1] = new Text(null, "to");
        elements[2] = new TextField(null, "high");
        elements[2].setHtmlName(rfield.getHighHtmlName());
        
        value = ((ValueRange)rfield.get()).get(0);
        for (Element element : elements) {
            if (!element.isDataStorable()) {
                element.setStyleClass("text_h");
                rfieldtag.addChild(TextRenderer.render((Text)element, config));
                continue;
            }
            
            input = (TextField)element;
            input.setObligatory(rfield.isObligatory());
            input.setSecret(rfield.isSecret());
            input.setLength(rfield.getLength());
            input.setModelItem(rfield.getModelItem());
            input.setEnabled(rfield.isEnabled());
            input.setDataElement(rfield.getDataElement());
            input.setSearchHelp(rfield.getSearchHelp());
            input.setLocale(rfield.getLocale());
            
            if (element.getName().equals("low"))
                input.set(value.getLow());
            else
                input.set(value.getHigh());
            
            rfieldtag.addChildren(TextFieldRenderer.render(input, config));
        }
        
        return rfieldtag;
    }
}
