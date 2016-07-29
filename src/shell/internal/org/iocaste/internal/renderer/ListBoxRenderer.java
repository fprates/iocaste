package org.iocaste.internal.renderer;

import java.util.Map;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.ListBox;
import org.iocaste.shell.common.Shell;

public class ListBoxRenderer extends AbstractElementRenderer<InputComponent> {

    public ListBoxRenderer(Map<Const, Renderer<?>> renderers) {
        super(renderers, Const.LIST_BOX);
    }
    
    @Override
    protected final XMLElement execute(InputComponent input, Config config) {
        Container container;
        Map<String, Object> values;
        XMLElement optiontag = null, selecttag= new XMLElement("select");
        String style, value, name = input.getHtmlName();
        
        switch (input.getType()) {
        case DATA_ITEM:
            values = ((DataItem)input).getValues();
            selecttag.add("style", "display:block");
            break;
        case LIST_BOX:
            values = ((ListBox)input).properties();
            break;
        default:
            values = null;
            break;
        }
        
        selecttag.add("name", name);
        selecttag.add("id", name);

        container = input.getContainer();
        if ((container != null) && (container.getType() == Const.TABLE_ITEM))
            style = "table_cell_content";
        else
            style = input.getStyleClass();
        
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
