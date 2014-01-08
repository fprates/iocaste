package org.iocaste.workbench.shell;

import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.workbench.Common;
import org.iocaste.workbench.Context;

public class Source {

    public static final String execute(String[] tokens, Context context) {
        Map<String, String> parameters;
        String packagename;
        String[] packagetokens;
        StringBuilder sb;
        ExtendedObject object;
        long lastsrcid;

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
        lastsrcid = object.getl("SOURCE_ID");
        
        /*
         * recupera dados do pacote
         */
        packagetokens = tokens[2].split("\\.");
        sb = new StringBuilder();
        for (int i = 0; i < packagetokens.length; i++) {
            if (i == (packagetokens.length - 1)) {
                context.projectsourcename = packagetokens[i];
                continue;
            }
            
            if (i > 0)
                sb.append(".");
            sb.append(packagetokens[i]);
        }
        packagename = sb.toString();
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
            context.projectsourceid = lastsrcid + 1;
            context.projectdefsource = parameters.get("--default");
            object = getSourceInstance(context);
            context.projectsources.put(context.projectfullsourcename, object);
            context.view.redirect("source");
            return "source.created";
        default:
            return "invalid.operation";
        }
    }
    
    private static final ExtendedObject getSourceInstance(Context context) {
        DocumentModel model = new Documents(context.function).
                getModel("WB_SOURCE");
        ExtendedObject object = new ExtendedObject(model);
        
        object.set("SOURCE_NAME", context.projectfullsourcename);
        object.set("PACKAGE_ID", context.projectpackageid);
        object.set("PROJECT_NAME", context.projectname);
        object.set("SOURCE_ID", context.projectsourceid);
        return object;
    }
}
