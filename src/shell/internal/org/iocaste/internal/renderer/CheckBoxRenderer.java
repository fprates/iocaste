package org.iocaste.internal.renderer;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.CheckBox;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.InputComponent;

public class CheckBoxRenderer extends Renderer {

    public static final XMLElement render(DataItem dataitem) {
        return _render(dataitem);
    }
    

    public static final XMLElement render(CheckBox checkbox) {
        return _render(checkbox);
    }
    
    /**
     * 
     * @param checkbox
     * @return
     */
    private static final XMLElement _render(InputComponent input) {
        XMLElement cboxtag = new XMLElement("input");
        String name = input.getHtmlName();
        
        cboxtag.add("type", "checkbox");
        cboxtag.add("name", name);
        cboxtag.add("id", name);
        
        if (!input.isEnabled())
            cboxtag.add("disabled", "disabled");
        
        addEvents(cboxtag, input);
        
        if (input.isSelected())
            cboxtag.add("checked", "checked");
        
        return cboxtag;
    }

}
