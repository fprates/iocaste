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

        /*
         * recupera dados do projeto
         */
        parameters = Common.getParameters(tokens, "--project");
        context.project = parameters.get("--project");
        if (context.project == null)
            return "unspecified.project";
        
        if (Common.getProject(context.project, context) == null)
            return "invalid.project";
        
        /*
         * recupera dados do pacote
         */
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
        object = Common.getPackage(packagename, context.project, context);
        if (object == null)
            return "invalid.package";
        
        context.packageid = object.getl("PACKAGE_ID");
        context.sources = Common.getSources(context.packageid, context);
        
        /*
         * recupera dados dos fontes
         */
        context.sourceid = context.packageid;
        
        context.fullsourcename = tokens[2];
        switch (tokens[1]) {
        case "edit":
            context.editormode = Context.EDIT;
            context.sourceid = context.sources.get(context.fullsourcename).
                    getl("SOURCE_ID");
            context.view.redirect("source");
            return "source.edited";
        case "create":
            if (context.sources.containsKey(context.fullsourcename))
                return "duplicate.source.name";

            context.editormode = Context.NEW;
            context.sourceid++;
            object = getSourceInstance(context);
            context.sources.put(context.fullsourcename, object);
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
        
        object.set("SOURCE_NAME", context.fullsourcename);
        object.set("PACKAGE_ID", context.packageid);
        object.set("PROJECT_NAME", context.project);
        object.set("SOURCE_ID", context.sourceid);
        return object;
    }
}
