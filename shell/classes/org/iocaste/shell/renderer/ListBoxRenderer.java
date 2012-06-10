package org.iocaste.shell.renderer;

import org.iocaste.shell.XMLElement;
import org.iocaste.shell.common.ListBox;
import org.iocaste.shell.common.Shell;

public class ListBoxRenderer extends Renderer {
    
    /**
     * 
     * @param list
     * @return
     */
    public static final XMLElement render(ListBox list) {
        String[] entriesnames;
        XMLElement optiontag = null, selecttag= new XMLElement("select");
        String value, name = list.getHtmlName();
        
        selecttag.add("name", name);
        selecttag.add("id", name);

        if (!list.isEnabled()) {
            selecttag.add("class", new StringBuilder(list.getStyleClass()).
                    append("_disabled").toString());
            selecttag.add("disabled", "disabled");
        } else {
            selecttag.add("class", list.getStyleClass());
        }
        
        addEvents(selecttag, list);
        
        entriesnames = list.getEntriesNames();
        if (entriesnames.length == 0)
            selecttag.addInner("");
        else
            for (String option : entriesnames) {
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
