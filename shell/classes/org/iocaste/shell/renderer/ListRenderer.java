package org.iocaste.shell.renderer;

import org.iocaste.shell.XMLElement;
import org.iocaste.shell.common.ListBox;

public class ListRenderer extends Renderer {
    
    /**
     * 
     * @param list
     * @return
     */
    public static final XMLElement render(ListBox list) {
        String value;
        XMLElement optiontag = null, selecttag= new XMLElement("select");
        String name = list.getHtmlName();
        
        selecttag.add("name", name);
        selecttag.add("id", name);

        addAttributes(selecttag, list);
        
        for (String option : list.getEntriesNames()) {
            optiontag = new XMLElement("option");
            value = list.get(option);
            optiontag.add("value", value);
            
            if (value.equals(list.getValue()))
                optiontag.add("selected", "selected");
            
            optiontag.addInner(option);
            
            selecttag.addChild(optiontag);
        }
        
        return selecttag;
    }
}
