package org.iocaste.workbench.shell;

import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.workbench.Context;

public class Package {

    private static final String create(String[] tokens, Context context) {
        int projectid;
        long packageid;
        String project, packagename;
        Query query;
        ExtendedObject object;
        ExtendedObject[] objects;
        DocumentModel model;
        Documents documents;
        Map<String, String> parameters;
        
        parameters = Common.getParameters(tokens, "--project");
        project = parameters.get("--project");
        if (project == null)
            return "project.unspecified";

        documents = new Documents(context.function);
        query = new Query();
        query.setModel("WB_PROJECT");
        query.andEqual("PROJECT_NAME", project);
        objects = documents.select(query);
        if (objects == null)
            return "invalid.project";
        
        projectid = objects[0].geti("PROJECT_ID");
        query = new Query();
        query.setModel("WB_PACKAGE");
        query.andEqual("PROJECT_NAME", project);
        objects = documents.select(query);
        if (objects == null)
            packageid = (projectid * 1000) + 1;
        else
            packageid = objects[objects.length - 1].getl("PACKAGE_ID") + 1;
        
        packagename = tokens[2];
        model = documents.getModel("WB_PACKAGE");
        object = new ExtendedObject(model);
        object.set("PACKAGE_NAME", packagename);
        object.set("PROJECT_NAME", project);
        object.set("PACKAGE_ID", packageid);
        documents.save(object);
        
        return "package.created";
    }
    public static final String execute(String[] tokens, Context context) {
        switch (tokens[1]) {
        case "create":
            return create(tokens, context);
        default:
            return "invalid.package.operation";
        }
    }
}
