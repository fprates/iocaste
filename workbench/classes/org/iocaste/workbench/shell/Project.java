package org.iocaste.workbench.shell;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.workbench.Common;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.compiler.Compile;

public class Project {
    
    public static final String execute(String[] tokens, Context context)
            throws Exception {
        context.projectdir = Common.composeFileName(
                context.repository, tokens[2]);
        
        switch (tokens[1]) {
        case "list":
            return list(tokens[2], context);
        case "create":
            return Common.createProject(tokens[2], context);
        case "remove":
            return remove(tokens[2]);
        case "compile":
            return Compile.execute(tokens[2], context);
        default:
            return "invalid.project.operation";
        }
    }
    
    private static final String list(String project, Context context) {
        String[] tokens;
        Query query;
        ExtendedObject[] objects;
        Documents documents;
        StringBuilder sb;
        
        if (Common.getProject(project, context) == null)
            return "invalid.project";
        
        documents = new Documents(context.function);
        query = new Query();
        query.setModel("WB_PACKAGE");
        query.andEqual("PROJECT_NAME", project);
        objects = documents.select(query);
        if (objects == null)
            return "project.has.no.packages";

        tokens = new String[4];
        tokens[0] = "package";
        tokens[1] = "list";
        tokens[3] = "--project=".concat(project);
        sb = new StringBuilder();
        for (ExtendedObject object :  objects) {
            if (sb.length() > 0)
                sb.append('\n');
            
            tokens[2] = object.getst("PACKAGE_NAME");
            sb.append("-".concat(tokens[2])).append('\n');
            sb.append(Package.execute(tokens, context));
        }
        
        return sb.toString();
    }
    
    private static final String remove(String project) {
        return "project.removed";
    }
}
