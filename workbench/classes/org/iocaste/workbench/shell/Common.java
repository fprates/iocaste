package org.iocaste.workbench.shell;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.workbench.Context;

public class Common {

    public static final ExtendedObject getPackage(String name, String project,
            Context context)
    {
        ExtendedObject[] objects;
        Documents documents = new Documents(context.function);
        Query query = new Query();
        
        query.setModel("WB_PACKAGE");
        query.setMaxResults(1);
        query.andEqual("PROJECT_NAME", context.project);
        query.andEqual("PACKAGE_NAME", name);
        objects = documents.select(query);
        return (objects == null)? null : objects[0];
    }
    
    public static final Map<String, String> getParameters(
            String[] tokens, String... names) {
        String absname;
        Map<String, String> parameters = new HashMap<>();
        
        for (String name : names) {
            absname = new StringBuilder(name).append('=').toString();
            for (int i = 0; i < tokens.length; i++) {
                if (!tokens[i].startsWith(absname))
                    continue;
                parameters.put(name, tokens[i].split("=")[1]);
                break;
            }
        }
        
        return parameters;
    }
    
    public static final ExtendedObject getProject(String name, Context context)
    {
        ExtendedObject[] objects;
        Documents documents = new Documents(context.function);
        Query query = new Query();
        
        query.setModel("WB_PROJECT");
        query.setMaxResults(1);
        query.andEqual("PROJECT_NAME", name);
        objects = documents.select(query);
        return (objects == null)? null : objects[0];
    }
    
    public static final ExtendedObject[] getSources(long packageid,
            Context context) {
        Documents documents = new Documents(context.function);
        Query query = new Query();
        
        query.setModel("WB_SOURCE");
        query.orderBy("PACKAGE_ID");
        query.andEqual("PACKAGE_ID", packageid);
        return documents.select(query);
    }
}
