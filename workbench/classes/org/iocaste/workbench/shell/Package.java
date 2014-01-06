package org.iocaste.workbench.shell;

import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.workbench.Context;

public class Package {

    private static final String create(String name, Context context) {
        int projectid;
        long packageid;
        Query query;
        ExtendedObject object;
        ExtendedObject[] objects;
        DocumentModel model;
        Documents documents;

        documents = new Documents(context.function);
        query = new Query();
        query.setModel("WB_PROJECT");
        query.andEqual("PROJECT_NAME", context.project);
        objects = documents.select(query);
        if (objects == null)
            return "invalid.project";
        
        projectid = objects[0].geti("PROJECT_ID");
        query = new Query();
        query.setModel("WB_PACKAGE");
        query.andEqual("PROJECT_NAME", context.project);
        objects = documents.select(query);
        if (objects == null)
            packageid = (projectid * 1000) + 1;
        else
            packageid = objects[objects.length - 1].getl("PACKAGE_ID") + 1;
        
        model = documents.getModel("WB_PACKAGE");
        object = new ExtendedObject(model);
        object.set("PACKAGE_NAME", name);
        object.set("PROJECT_NAME", context.project);
        object.set("PACKAGE_ID", packageid);
        documents.save(object);
        
        return "package.created";
    }
    
    public static final String execute(String[] tokens, Context context) {
        Map<String, String> parameters;

        parameters = Common.getParameters(tokens, "--project");
        context.project = parameters.get("--project");
        if (context.project == null)
            return "project.unspecified";
        
        switch (tokens[1]) {
        case "create":
            return create(tokens[2], context);
        case "list":
            return list(tokens[2], context);
        default:
            return "invalid.package.operation";
        }
    }
    
    private static final String list(String name, Context context) {
        StringBuilder sb;
        ExtendedObject[] objects;
        ExtendedObject object;
        
        object = Common.getPackage(name, context.project, context);
        if (object == null)
            return "invalid.package";
        
        objects = Common.getSources(object.getl("PACKAGE_ID"), context);
        if (objects == null)
            return "package.has.no.sources";
        
        sb = new StringBuilder();
        for (ExtendedObject object_ : objects) {
            if (sb.length() > 0)
                sb.append('\n');
            
            sb.append("--").append(object_.getst("SOURCE_NAME"));
        }
        
        return sb.toString();
    }
}
