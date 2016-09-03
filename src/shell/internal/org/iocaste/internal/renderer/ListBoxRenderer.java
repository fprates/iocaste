package org.iocaste.internal.renderer;

import java.util.Map;

import org.iocaste.internal.renderer.listbox.ListBoxDataFormSource;
import org.iocaste.internal.renderer.listbox.ListBoxDataItemSource;
import org.iocaste.internal.renderer.listbox.ListBoxSource;
import org.iocaste.internal.renderer.listbox.ListBoxTableItemSource;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Shell;

public class ListBoxRenderer extends AbstractElementRenderer<InputComponent> {
    
    public ListBoxRenderer(Map<Const, Renderer<?>> renderers) {
        super(renderers, Const.LIST_BOX);
        put(Const.DATA_ITEM, new ListBoxDataItemSource());
        put(Const.LIST_BOX, new ListBoxSource());
        put(Const.TABLE_ITEM, new ListBoxTableItemSource());
        put(Const.DATA_FORM, new ListBoxDataFormSource());
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected final XMLElement execute(InputComponent input, Config config) {
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
        if (container != null) {
            source = getSource(container.getType());
            if (source != null) {
                source.set("container", container);
                source.set("input", input);
                style = (String)source.run();
            }
        }
        
        if (style == null) {
            style = input.getStyleClass();
            if (style == null)
                style = Const.LIST_BOX.style();
        }
        
        selecttag.add("name", name);
        selecttag.add("id", name);
        
        if (!input.isEnabled()) {
            selecttag.add("class", style.concat("_disabled"));
            selecttag.add("disabled", "disabled");
        } else {
            selecttag.add("class", style);
        }
        
        addAttributes(selecttag, input);
        
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
