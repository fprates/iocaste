package org.iocaste.kernel.runtime.shell.renderer;

import java.util.Map;

import org.iocaste.kernel.runtime.shell.renderer.internal.ActionEventHandler;
import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.kernel.runtime.shell.renderer.internal.Source;
import org.iocaste.kernel.runtime.shell.renderer.listbox.ListBoxContainerSource;
import org.iocaste.kernel.runtime.shell.renderer.listbox.ListBoxDataFormSource;
import org.iocaste.kernel.runtime.shell.renderer.listbox.ListBoxDataItemSource;
import org.iocaste.kernel.runtime.shell.renderer.listbox.ListBoxSource;
import org.iocaste.kernel.runtime.shell.renderer.listbox.ListBoxTableItemSource;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.handlers.OnFocusHandler;

public class ListBoxRenderer extends AbstractElementRenderer<InputComponent> {
    
    public ListBoxRenderer(HtmlRenderer renderer) {
        super(renderer, Const.LIST_BOX);
        put(Const.DATA_ITEM, new ListBoxDataItemSource());
        put(Const.LIST_BOX, new ListBoxSource());
        put(Const.TABLE_ITEM, new ListBoxTableItemSource());
        put(Const.DATA_FORM, new ListBoxDataFormSource());
        put(new ListBoxContainerSource());
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected final XMLElement execute(InputComponent input, Config config) {
    	ActionEventHandler handler;
        Container container;
        Map<String, Object> values;
        Source source;
        XMLElement optiontag = null, selecttag = new XMLElement("select");
        String style, value, name = input.getHtmlName();
        
        source = getSource(input.getType());
        if (source != null) {
            source.set("select", selecttag);
            source.set("input", input);
            values = (Map<String, Object>)source.run();
        } else {
            values = null;
        }

        style = null;
        container = input.getContainer();
        source = (container != null)?
                getSource(container.getType()) : getSource();
        source.set("container", container);
        source.set("input", input);
        style = (String)source.run();
        
        selecttag.add("name", name);
        selecttag.add("id", name);
        
        if (!input.isEnabled()) {
            selecttag.add("class", style.concat("_disabled"));
            selecttag.add("disabled", "disabled");
        } else {
            selecttag.add("class", style);
        }
        
        handler = config.viewctx.getEventHandler(name, name, "focus");
        handler.name = name;
        handler.call = Shell.send(name, "&event=focus");
        input.put("focus", new OnFocusHandler(input));
        
        addAttributes(selecttag, input);

        if (input.hasPlaceHolder())
            selecttag.add("style", "text-align:center;width:100%;");
        
        if (values.size() == 0) {
            selecttag.addInner("");
            return selecttag;
        }
        
        for (String option : values.keySet()) {
            optiontag = new XMLElement("option");
            value = Shell.toString(values.get(option),
                    Shell.getDataElement(input), input.getLocale(), false);
            
            optiontag.add("value", value);
            
            if (value.equals(toString(input)))
                optiontag.add("selected", "selected");
            
            optiontag.addInner(option);
            
            selecttag.addChild(optiontag);
        }
        
        return selecttag;
    }
}
