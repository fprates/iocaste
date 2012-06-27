package org.iocaste.shell;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;

public class Style {

    private static final Object[] checkedSelect(Iocaste iocaste, String from,
            Map<String, String[]> ijoin, String where, Object... criteria)
                    throws Exception {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        parameters.put("from", from);
        parameters.put("where", where);
        parameters.put("criteria", criteria);
        parameters.put("inner_join", ijoin);
        return (Object[])iocaste.callAuthorized("checked_select",
                parameters);
    }
    
    /**
     * 
     * @param name
     * @param function
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static final Map<String, Map<String, String>> get(
            String name, Function function) throws Exception {
        String element;
        Object[] eobjects;
        Map<String, Object> oelement;
        Map<String, String> attributes;
        Map<String, Map<String, String>> elements =
                new LinkedHashMap<String, Map<String, String>>();
        Map<String, String[]> ijoin = new LinkedHashMap<String, String[]>();
        
        ijoin.put("SHELL003", new String[] {"SHELL002.EINDX = SHELL003.EINDX"});
        eobjects = checkedSelect(new Iocaste(function),
                "SHELL002", ijoin, "SHELL002.sname = ?", name);
        
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
