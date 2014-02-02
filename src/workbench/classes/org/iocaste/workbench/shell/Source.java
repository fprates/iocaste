package org.iocaste.workbench.shell;

import java.util.Map;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.workbench.Common;
import org.iocaste.workbench.Context;

public class Source {
    
    public static final String execute(String[] tokens, Context context) {
        Map<String, String> parameters;
        String packagename;
        ExtendedObject object;

        /*
         * recupera dados do projeto
         */
        parameters = Common.getParameters(tokens, "--project", "--default");
        context.projectname = parameters.get("--project");
        if (context.projectname == null)
            return "unspecified.project";
        
        object = Common.getProject(context.projectname, context);
        if (object == null)
            return "invalid.project";

        context.projectsourceobj = object.get("SOURCE_OBJ");
        
        /*
         * recupera dados do pacote
         */
        packagename = Common.extractPackageName(tokens[2]);
        context.projectsourcename = tokens[2].
                substring(packagename.length() + 1);
        
        object = Common.getPackage(packagename, context.projectname, context);
        if (object == null)
            return "invalid.package";
        
        context.projectpackageid = object.getl("PACKAGE_ID");
        context.projectsources = Common.getSources(
                context.projectpackageid, context);
        
        /*
         * recupera dados dos fontes
         */
        context.projectfullsourcename = tokens[2];
        switch (tokens[1]) {
        case "edit":
            object = context.projectsources.get(
                    context.projectfullsourcename);
            if (object == null)
                return "source.not.found";
            
            context.editormode = Context.EDIT;
            context.projectsourceid = object.getl("SOURCE_ID");
            context.view.redirect("source");
            context.projectdefsource = parameters.get("--default");
            return "source.modified";
        case "create":
            if (context.projectsources.containsKey(
                    context.projectfullsourcename))
                return "duplicated.source.name";

            context.editormode = Context.NEW;
            context.projectdefsource = parameters.get("--default");
            object = Common.getSourceInstance(context);
            context.projectsources.put(context.projectfullsourcename, object);
            context.view.redirect("source");
            return "source.created";
        default:
            return "invalid.operation";
        }
    }
}
