package org.iocaste.workbench.shell;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.workbench.Context;

public class Project {
    private static final String create(String project, Context context) {
        DocumentModel model;
        ExtendedObject object;
        long projectid;
        Documents documents = new Documents(context.function);
        
        projectid = documents.getNextNumber("WBPROJECTID");
        model = documents.getModel("WB_PROJECT");
        object = new ExtendedObject(model);
        object.set("PROJECT_NAME", project.toUpperCase());
        object.set("PROJECT_ID", projectid);
        documents.save(object);
        
        return "project.created";
    }
    
    public static final String execute(String[] tokens, Context context) {
        switch (tokens[1]) {
        case "create":
            return create(tokens[2], context);
        case "remove":
            return remove(tokens[2]);
        default:
            return "invalid.project.operation";
        }
    }
    
    private static final String remove(String project) {
        return "project.removed";
    }
}
