package org.iocaste.external;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class Test extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Map<String, Map<String, Object>> result;
        Map<String, Object> parameters;
        
        result = new HashMap<>();
        for (String keylist : new String[] {
                "importing",
                "exporting",
                "changing",
                "tables"
        }) {
            parameters = message.get(keylist);
            if (parameters == null)
                continue;

            result.put(keylist, parameters);
            for (String key : parameters.keySet())
                System.out.println(key + "=" + parameters.get(key).toString());
        }
        
        System.out.println("Congratulations! You got here!");
        return result;
    }

}
