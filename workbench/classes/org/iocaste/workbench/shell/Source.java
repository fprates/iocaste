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
        Documents documents = new Documents(context.function);

        /*
         * recupera dados do projeto
         */
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
        
        /*
         * recupera dados dos fontes
         */
        context.sourceid = context.packageid;
        if (objects != null)
            for (ExtendedObject object : objects) {
                context.fullsourcename = object.get("SOURCE_NAME");
                context.sourceid = object.geti("SOURCE_ID");
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
