package org.iocaste.shell;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.protocol.Function;

public class Style {
    
    /**
     * 
     * @param name
     * @param function
     * @return
     */
    @SuppressWarnings("unchecked")
    public static final Map<String, Map<String, String>> get(
            String name, Function function) {
        String element;
        Object[] eobjects;
        Map<String, Object> oelement;
        Map<String, String> attributes;
        Map<String, Map<String, String>> elements = new LinkedHashMap<>();
        Map<String, String[]> ijoin = new LinkedHashMap<>();
        CheckedSelect select = new CheckedSelect(function);
        
        ijoin.put("SHELL003", new String[] {"SHELL002.EINDX = SHELL003.EINDX"});
        select.setFrom("SHELL002");
        select.setInnerJoin(ijoin);
        select.setWhere("SHELL002.sname = ?", name);
        eobjects = select.execute();
        
        for (Object eobject : eobjects) {
            oelement = (Map<String, Object>)eobject;
            element = (String)oelement.get("ENAME");
            if (elements.containsKey(element)) {
                attributes = elements.get(element);
            } else {
                attributes = new HashMap<String, String>();
                elements.put(element, attributes);
            }
            
            attributes.put((String)oelement.get("PNAME"),
                    (String)oelement.get("VALUE"));
        }
        
        return elements;
    }
}
