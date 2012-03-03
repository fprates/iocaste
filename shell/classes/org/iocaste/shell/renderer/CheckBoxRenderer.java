package org.iocaste.shell.renderer;

import org.iocaste.shell.XMLElement;
import org.iocaste.shell.common.CheckBox;

public class CheckBoxRenderer extends Renderer {

    /**
     * 
     * @param checkbox
     * @return
     */
    public static final XMLElement render(CheckBox checkbox) {
        XMLElement cboxtag = new XMLElement("input");
        String name = checkbox.getHtmlName();
        
        cboxtag.add("type", "checkbox");
        cboxtag.add("name", name);
        cboxtag.add("id", name);
        
        addAttributes(cboxtag, checkbox);
        
        if (checkbox.isSelected())
            cboxtag.add("checked", "checked");
        
        return cboxtag;
    }

}
