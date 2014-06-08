package org.iocaste.workbench.shell;

import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.workbench.Common;
import org.iocaste.workbench.Context;

public class Package {

//    public static final String create(String name, Context context) {
//        int projectid;
//        long packageid;
//        ExtendedObject object;
//        ExtendedObject[] objects;
//        DocumentModel model;
//        Documents documents;
//
//        object = Common.getProject(context.projectname, context);
//        if (object == null)
//            return "invalid.project";
//
//        projectid = object.geti("PROJECT_ID");
//        object = Common.getPackage(name, context.projectname, context);
//        if (object != null)
//            return "package.has.already.exist";
//
//        objects = Common.getPackages(context.projectname, context);
//        if (objects == null)
//            packageid = (projectid * 1000) + 1;
//        else
//            packageid = objects[objects.length - 1].getl("PACKAGE_ID") + 1;
//        
//        documents = new Documents(context.function);
//        model = documents.getModel("WB_PACKAGE");
//        object = new ExtendedObject(model);
//        object.set("PACKAGE_NAME", name);
//        object.set("PROJECT_NAME", context.projectname);
//        object.set("PACKAGE_ID", packageid);
//        documents.save(object);
//        
//        return "package.created";
//    }
//    
//    public static final String execute(String[] tokens, Context context) {
//        Map<String, String> parameters;
//
//        parameters = Common.getParameters(tokens, "--project");
//        context.projectname = parameters.get("--project");
//        if (context.projectname == null)
//            return "project.unspecified";
//        
//        switch (tokens[1]) {
//        case "create":
//            return create(tokens[2], context);
//        case "list":
//            return list(tokens[2], context);
//        default:
//            return "invalid.package.operation";
//        }
//    }
//    
//    private static final String list(String name, Context context) {
//        StringBuilder sb;
//        Map<String, ExtendedObject> objects;
//        ExtendedObject object;
//        
//        object = Common.getPackage(name, context.projectname, context);
//        if (object == null)
//            return "invalid.package";
//        
//        objects = Common.getSources(object.getl("PACKAGE_ID"), context);
//        if (objects == null)
//            return "package.has.no.sources";
//        
//        sb = new StringBuilder();
//        for (ExtendedObject object_ : objects.values()) {
//            if (sb.length() > 0)
//                sb.append('\n');
//            
//            sb.append("---").append(object_.getst("SOURCE_NAME"));
//        }
//        
//        return sb.toString();
//    }
}
