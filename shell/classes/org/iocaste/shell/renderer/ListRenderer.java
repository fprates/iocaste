package org.iocaste.shell.renderer;

import org.iocaste.shell.XMLElement;
import org.iocaste.shell.common.ListBox;
import org.iocaste.shell.common.Shell;

public class ListRenderer extends Renderer {
    
    /**
     * 
     * @param list
     * @return
     */
    public static final XMLElement render(ListBox list) {
        XMLElement optiontag = null, selecttag= new XMLElement("select");
        String value, name = list.getHtmlName();
        
        selecttag.add("name", name);
        selecttag.add("id", name);

        if (!list.isEnabled())
            selecttag.add("disabled", "disabled");
        
        addAttributes(selecttag, list);
        
        for (String option : list.getEntriesNames()) {
            optiontag = new XMLElement("option");
            value = toString(list.get(option), Shell.getDataElement(list),
                    list.getLocale(), false);
            
            optiontag.add("value", value);
            
            if (value.equals(toString(list)))
                optiontag.add("selected", "selected");
            
            optiontag.addInner(option);
            
            selecttag.addChild(optiontag);
        }
        
        return selecttag;
    }
}
