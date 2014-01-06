package org.iocaste.workbench.shell;

import java.util.Map;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.workbench.Context;

public class Source {

    public static final String execute(String[] tokens, Context context) {
        Map<String, String> parameters;
        String packagename;
        String[] packagetokens;
        StringBuilder sb;
        ExtendedObject object;
        ExtendedObject[] objects;

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
        objects = Common.getSources(context.packageid, context);
        
        /*
         * recupera dados dos fontes
         */
        context.sources.clear();
        context.sourceid = context.packageid;
        if (objects != null)
            for (ExtendedObject object_ : objects) {
                context.fullsourcename = object_.get("SOURCE_NAME");
                context.sourceid = object_.geti("SOURCE_ID");
                context.sources.put(context.fullsourcename, context.sourceid);
            }
        
        context.fullsourcename = tokens[2];
        switch (tokens[1]) {
        case "edit":
            context.editormode = Context.EDIT;
            context.sourceid = context.sources.get(context.fullsourcename);
            context.view.redirect("source");
            return "source.edited";
        case "create":
            if (context.sources.containsKey(context.fullsourcename))
                return "duplicate.source.name";
            
            context.editormode = Context.NEW;
            context.sourceid++;
            context.sources.put(context.fullsourcename, context.sourceid);
            context.view.redirect("source");
            return "source.created";
        default:
            return "invalid.operation";
        }
    }
}
