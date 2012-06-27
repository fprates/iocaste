package org.iocaste.shell;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;

public class Style {

    private static final Object[] checkedSelect(Iocaste iocaste, String from,
            String where, Object... criteria) throws Exception {
        Map<String, Object> parameters = new HashMap<String, Object>();
        
        parameters.put("from", from);
        parameters.put("where", where);
        parameters.put("criteria", criteria);
        
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
        long index;
        Object[] eobjects, aobjects;
        Map<String, Object> oelement, oattribute;
        Map<String, String> attributes;
        Iocaste iocaste = new Iocaste(function);
        Map<String, Map<String, String>> elements =
                new LinkedHashMap<String, Map<String, String>>();
        
        eobjects = checkedSelect(iocaste, "SHELL002", "sname = ?", name);
        
        for (Object eobject : eobjects) {
            oelement = (Map<String, Object>)eobject;
            index = ((BigDecimal)oelement.get("EINDX")).longValue();
            aobjects = checkedSelect(iocaste, "SHELL003", "eindx = ?", index);
            
            attributes = new HashMap<String, String>();
            
            for (Object aobject : aobjects) {
                oattribute = (Map<String, Object>)aobject;
                attributes.put((String)oattribute.get("PNAME"),
                        (String)oattribute.get("VALUE"));
            }
            
            elements.put((String)oelement.get("ENAME"), attributes);
        }
        
        return elements;
    }
}
