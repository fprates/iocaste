package org.iocaste.kernel.runtime.shell.renderer;

import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;

public class CheckBoxRenderer extends AbstractElementRenderer<InputComponent> {
    
    public CheckBoxRenderer(HtmlRenderer renderer) {
        super(renderer, Const.CHECKBOX);
    }

    /**
     * 
     * @param checkbox
     * @return
     */
    protected final XMLElement execute(InputComponent input, Config config) {
        XMLElement cboxtag = new XMLElement("input");
        String name = input.getHtmlName();
        
        cboxtag.add("type", "checkbox");
        cboxtag.add("name", name);
        cboxtag.add("id", name);
        cboxtag.add("class", getStyle(input));
        
        if (!input.isEnabled())
            cboxtag.add("disabled", "disabled");
        
        addAttributes(cboxtag, input);
        
        if (input.isSelected())
            cboxtag.add("checked", "checked");
        
        return cboxtag;
    }

}
