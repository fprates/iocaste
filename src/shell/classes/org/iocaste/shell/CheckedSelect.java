package org.iocaste.shell;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;

public class CheckedSelect {
    private Function function;
    private String from, where;
    private Map<String, String[]> ijoin;
    private Object[] values;
    
    public CheckedSelect(Function function) {
        this.function = function;
    }
    
    public final void setFrom(String from) {
        this.from = from;
    }
    
    public final void setInnerJoin(Map<String, String[]> ijoin) {
        this.ijoin = ijoin;
    }
    
    public final void setWhere(String where, Object... values) {
        this.where = where;
        this.values = values;
    }
    
    public final Object[] execute() {
        Iocaste iocaste = new Iocaste(function);
        Map<String, Object> parameters = new HashMap<>();
        
        parameters.put("from", from);
        if (where != null)
            parameters.put("where", where);
        
        if (values!= null)
            parameters.put("criteria", values);
        
        if (ijoin != null)
            parameters.put("inner_join", ijoin);
        
        return (Object[])iocaste.callAuthorized("checked_select",
                parameters);
    }

}
