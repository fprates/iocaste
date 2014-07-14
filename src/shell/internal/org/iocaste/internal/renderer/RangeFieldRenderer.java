package org.iocaste.internal.renderer;

import org.iocaste.documents.common.ValueRange;
import org.iocaste.documents.common.ValueRangeItem;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.RangeField;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.TextField;

public class RangeFieldRenderer extends Renderer {

    public static final XMLElement render(DataItem dataitem, Config config) {
        return _render(dataitem, RangeField.LOW, RangeField.HIGH, config);
    }
    
    public static final XMLElement render(RangeField rfield, Config config) {
        return _render(rfield, rfield.getLowHtmlName(),
                rfield.getHighHtmlName(), config);
    }
    
    private static final XMLElement _render(InputComponent input,
            String low, String high, Config config)
    {
        ValueRange range;
        ValueRangeItem value;
        TextField tfield;
        Element[] elements = new Element[3];
        XMLElement rfieldtag = new XMLElement("div");
        
        elements[0] = new TextField(config.getView(), "low");
        elements[0].setHtmlName(low);
        elements[1] = new Text(config.getView(), "to");
        elements[2] = new TextField(config.getView(), "high");
        elements[2].setHtmlName(high);
        
        range = (ValueRange)input.get();
        value = (range == null)? null : range.get(0);
        for (Element element : elements) {
            if (!element.isDataStorable()) {
                element.setStyleClass("text_h");
                rfieldtag.addChild(TextRenderer.render((Text)element, config));
                continue;
            }
            
            tfield = (TextField)element;
            tfield.setObligatory(input.isObligatory());
            tfield.setSecret(input.isSecret());
            tfield.setLength(input.getLength());
            tfield.setModelItem(input.getModelItem());
            tfield.setEnabled(input.isEnabled());
            tfield.setDataElement(input.getDataElement());
            tfield.setSearchHelp(input.getSearchHelp());
            tfield.setLocale(input.getLocale());
            
            if (element.getName().equals("low"))
                tfield.set((value == null)? null : value.getLow());
            else
                tfield.set((value == null)? null : value.getHigh());
            
            rfieldtag.addChildren(TextFieldRenderer.render(tfield, config));
        }
        
        return rfieldtag;
    }
}
