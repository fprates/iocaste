package org.iocaste.internal.renderer;

import java.util.Map;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.ListBox;
import org.iocaste.shell.common.Shell;

public class ListBoxRenderer extends Renderer {

    public static final XMLElement render(DataItem dataitem) {
        return _render(dataitem, dataitem.getValues());
    }

    public static final XMLElement render(ListBox list) {
        return _render(list, list.properties());
    }
    
    /**
     * 
     * @param list
     * @return
     */
    private static final XMLElement _render(InputComponent input,
            Map<String, Object> values) {
        XMLElement optiontag = null, selecttag= new XMLElement("select");
        String value, name = input.getHtmlName();
        
        selecttag.add("name", name);
        selecttag.add("id", name);

        if (!input.isEnabled()) {
            selecttag.add("class", new StringBuilder(input.getStyleClass()).
                    append("_disabled").toString());
            selecttag.add("disabled", "disabled");
        } else {
            selecttag.add("class", input.getStyleClass());
        }
        
        addEvents(selecttag, input);
        
        if (values.size() == 0)
            selecttag.addInner("");
        else
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
