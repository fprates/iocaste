package org.iocaste.kernel.runtime.shell.renderer.legacy;

import org.iocaste.kernel.runtime.shell.renderer.AbstractElementRenderer;
import org.iocaste.kernel.runtime.shell.renderer.internal.ActionEventHandler;
import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.handlers.OnFocusHandler;

public class CheckBoxRenderer extends AbstractElementRenderer<InputComponent> {
    
    public CheckBoxRenderer(HtmlRenderer renderers) {
        super(renderers, Const.CHECKBOX);
    }

    /**
     * 
     * @param checkbox
     * @return
     */
    protected final XMLElement execute(InputComponent input, Config config) {
        ActionEventHandler handler;
        XMLElement cboxtag = new XMLElement("input");
        String name = input.getHtmlName();
        
        cboxtag.add("type", "checkbox");
        cboxtag.add("name", name);
        cboxtag.add("id", name);
        cboxtag.add("class", getStyle(input));
        
        handler = config.viewctx.getEventHandler(name, name, "focus");
        handler.name = name;
        handler.call = Shell.send(name,"&event=focus");
        input.put("focus", new OnFocusHandler(input));
        
        if (!input.isEnabled())
            cboxtag.add("disabled", "disabled");
        
        addAttributes(cboxtag, input);
        
        if (input.isSelected())
            cboxtag.add("checked", "checked");
        
        return cboxtag;
    }

}
