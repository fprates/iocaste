package org.iocaste.shell.renderer;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.shell.XMLElement;
import org.iocaste.shell.common.RadioButton;

public class RadioButtonRenderer extends Renderer {

    /**
     * 
     * @param radiobutton
     * @return
     */
    public static final List<XMLElement> render(RadioButton radiobutton) {
        String text;
        XMLElement rbtag, rbtexttag;
        List<XMLElement> elements = new ArrayList<XMLElement>();
        
        text = radiobutton.getHtmlName();
        rbtag = new XMLElement("input");
        rbtag.add("type", "radio");
        rbtag.add("name", text);
        rbtag.add("value", radiobutton.getName());
        rbtag.add("id", text);
        
        if (radiobutton.isSelected())
            rbtag.add("checked", "checked");
        
        addEvents(rbtag, radiobutton);
        elements.add(rbtag);
        
        text = radiobutton.getText();
        if (text != null) {
            rbtexttag = new XMLElement("span");
            rbtexttag.addInner(text);
            elements.add(rbtexttag);
        }
        
        return elements;
    }
}
