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
        List<XMLElement> elements = new ArrayList<XMLElement>();
        Map<String, String> values = radiobutton.getValues();
        XMLElement rbtag;
        
        for (String key : values.keySet()) {
            rbtag = new XMLElement("input");
            rbtag.add("type", "radio");
            rbtag.add("name", radiobutton.getHtmlName());
            rbtag.add("value", key);
            rbtag.addInner(values.get(key));
            
            if (radiobutton.getValue().equals(key))
                rbtag.add("checked", "checked");
            
            elements.add(rbtag);
            
            if (radiobutton.getAlignment() == RadioButton.VERTICAL)
                elements.add(new XMLElement("br"));
        }
        
        return elements;
    }
}
