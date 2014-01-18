package org.iocaste.workbench;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.texteditor.common.TextEditorTool;

public class Common {

    public static final String composeFileName(String... names) {
        StringBuilder sb = new StringBuilder();
        
        for (String name : names) {
            if (sb.length() > 0)
                sb.append(File.separator);
        
            sb.append(name);
        }
        
        return sb.toString();
    }
    
    public static final String createProject(String project, Context context) {
        DocumentModel model;
        ExtendedObject object;
        long projectid;
        String textname;
        Documents documents = new Documents(context.function);
        TextEditorTool tetool = new TextEditorTool(context);
        
        projectid = documents.getNextNumber("WBPROJECTID");
        textname = new StringBuilder("WBPRJ").append(projectid).toString();
        tetool.register(textname);
        
        model = documents.getModel("WB_PROJECT");
        object = new ExtendedObject(model);
        object.set("PROJECT_NAME", project.toUpperCase());
        object.set("PROJECT_ID", projectid);
        object.set("SOURCE_OBJ", textname);
        documents.save(object);
        
        return "project.created";
    }
    
    public static final ExtendedObject getPackage(String name, String project,
            Context context)
    {
        ExtendedObject[] objects;
        Documents documents = new Documents(context.function);
        Query query = new Query();
        
        query.setModel("WB_PACKAGE");
        query.setMaxResults(1);
        query.andEqual("PROJECT_NAME", project);
        query.andEqual("PACKAGE_NAME", name);
        objects = documents.select(query);
        return (objects == null)? null : objects[0];
    }
    
    public static final ExtendedObject[] getPackages(String project,
            Context context) {
        Documents documents = new Documents(context.function);
        Query query = new Query();
        
        query.setModel("WB_PACKAGE");
        query.andEqual("PROJECT_NAME", project);
        return documents.select(query);
    }
    
    public static final Map<String, String> getParameters(
            String[] tokens, String... names) {
        String parname;
        Map<String, String> parameters = new HashMap<>();
        
        for (String name : names) {
            parname = new StringBuilder(name).append('=').toString();
            for (int i = 0; i < tokens.length; i++) {
                if (tokens[i].startsWith(parname)) {
                    parameters.put(name, tokens[i].split("=")[1]);
                    break;
                }
                
                if (tokens[i].equals(name)) {
                    parameters.put(name, "true");
                    break;
                }
            }
        }
        
        return parameters;
    }
    
    public static final ExtendedObject getProject(String name, Context context)
    {
        ExtendedObject[] objects;
        Documents documents = new Documents(context.function);
        Query query = new Query();
        
        query.setModel("WB_PROJECT");
        query.setMaxResults(1);
        query.andEqual("PROJECT_NAME", name);
        objects = documents.select(query);
        return (objects == null)? null : objects[0];
    }
    
    public static final Map<String, ExtendedObject> getSources(long packageid,
            Context context) {
        String fullsourcename;
        ExtendedObject[] objects;
        Documents documents = new Documents(context.function);
        Map<String, ExtendedObject> sources = new HashMap<>();
        Query query = new Query();
        
        query.setModel("WB_SOURCE");
        query.orderBy("PACKAGE_ID");
        query.andEqual("PACKAGE_ID", packageid);
        objects = documents.select(query);
        if (objects == null)
            return sources;
        
        for (ExtendedObject object_ : objects) {
            fullsourcename = object_.get("SOURCE_NAME");
            sources.put(fullsourcename, object_);
        }
        
        return sources;
    }
    
    public static final ExtendedObject getSourceInstance(Context context) {
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
