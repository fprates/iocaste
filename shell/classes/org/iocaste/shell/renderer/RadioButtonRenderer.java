package org.iocaste.shell.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.iocaste.shell.XMLElement;
import org.iocaste.shell.common.RadioButton;

public class RadioButtonRenderer extends Renderer {

    /**
     * 
     * @param radiobutton
     * @return
     */
    public static final List<XMLElement> render(RadioButton radiobutton) {
        XMLElement rbtag;
        String value;
        List<XMLElement> elements = new ArrayList<XMLElement>();
        Map<String, String> values = radiobutton.getValues();
        
        for (String key : values.keySet()) {
            value = radiobutton.getHtmlName();
            rbtag = new XMLElement("input");
            rbtag.add("type", "radio");
            rbtag.add("name", value);
            rbtag.add("value", key);
            rbtag.add("id", value);
            
            value = values.get(key);
            if (value != null)
                rbtag.addInner(value);
            
            value = toString(radiobutton);
            if (value != null && value.equals(key))
                rbtag.add("checked", "checked");
            
            addEvents(rbtag, radiobutton);
            
            elements.add(rbtag);
            
            if (radiobutton.getAlignment() == RadioButton.VERTICAL)
                elements.add(new XMLElement("br"));
        }
        
        return elements;
    }
}
