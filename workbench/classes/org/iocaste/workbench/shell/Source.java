package org.iocaste.workbench.shell;

import java.util.Map;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.workbench.Context;

public class Source {

    public static final String execute(String[] tokens, Context context) {
        Query query;
        Map<String, String> parameters;
        String packagename;
        String[] packagetokens;
        StringBuilder sb;
        ExtendedObject[] objects;
        long sourceid;
        Documents documents = new Documents(context.function);
        
        switch (tokens[1]) {
        case "create":
            parameters = Common.getParameters(tokens, "--project");
            context.project = parameters.get("--project");
            if (context.project == null)
                return "unspecified.project";
            
            query = new Query();
            query.setModel("WB_PROJECT");
            query.andEqual("PROJECT_NAME", context.project);
            objects = documents.select(query);
            if (objects == null)
                return "invalid.project";
            
            packagetokens = tokens[2].split("\\.");
            sb = new StringBuilder();
            for (int i = 0; i < packagetokens.length; i++) {
                if (i == (packagetokens.length - 1)) {
                    context.sourcename = packagetokens[i];
                    continue;
                }
                
                if (i > 0)
                    sb.append(".");
                sb.append(packagetokens[i]);
            }
            packagename = sb.toString();
            
            query = new Query();
            query.setModel("WB_PACKAGE");
            query.andEqual("PROJECT_NAME", context.project);
            query.andEqual("PACKAGE_NAME", packagename);
            objects = documents.select(query);
            if (objects == null)
                return "invalid.package";

            context.sources.clear();
            context.packageid = objects[0].getl("PACKAGE_ID");
            query = new Query();
            query.setModel("WB_SOURCE");
            query.orderBy("PACKAGE_ID");
            query.andEqual("PACKAGE_ID", context.packageid);
            objects = documents.select(query);
            sourceid = context.packageid;
            if (objects != null)
                for (ExtendedObject object : objects) {
                    context.fullsourcename = object.get("SOURCE_NAME");
                    if (context.fullsourcename == tokens[2])
                        return "duplicate.source.name";
                    
                    sourceid = object.geti("SOURCE_ID");
                    context.sources.put(context.fullsourcename, sourceid);
                }
            
            context.fullsourcename = tokens[2];
            context.sources.put(context.fullsourcename, sourceid + 1);
            context.view.redirect("source");
            break;
        }
        return "source.created";
    }
}
