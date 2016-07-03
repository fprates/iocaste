package org.iocaste.workbench.project;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class ProjectList extends AbstractCommand {

    public ProjectList(Context extcontext) {
        super("project-list", extcontext);
        checkproject = false;
    }
    
    @Override
    protected Object entry(PageBuilderContext context) throws Exception {
        String projectname;
        ExtendedObject object;
        ComplexDocument[] projects;
        
        projects = getProjects(context);
        if (projects == null) {
            message(Const.ERROR, "no.project.available");
            return null;
        }
        
//        for (ComplexDocument project : projects) {
//            object = project.getHeader();
//            projectname = project.getstKey();
//            print("- Nome: %s", object.getst("PROJECT_NAME"));
//            
//            print("- Perfil: %s", object.getst("PROFILE"));
//            
//            print("- Telas");
//            for (ComplexDocument screen : project.getDocuments("screen"))
//                print(screen.getstKey());
//            
//            print("- Links");
//            for (ExtendedObject link : project.getItems("link"))
//                print(link.getst("NAME"));
//            
//            print("- Elementos de dados");
//            printddojects("WB_DATA_ELEMENTS", projectname);
//            
//            print("- Modelos");
//            for (ComplexDocument document : project.getDocuments("model"))
//                print(document.getstKey());
//            
//            print("- Pacotes");
//            for (ComplexDocument document : project.getDocuments("class"))
//                print(document.getstKey());
//        }
        
        return projects;
    }
    
    private final ComplexDocument[] getProjects(PageBuilderContext context) {
        String projectname;
        Query query;
        ExtendedObject[] objects;
        ComplexDocument[] projects;
        
        query = new Query();
        query.setModel("WB_PROJECT_HEAD");
        objects = select(query);
        if (objects == null)
            return null;
        projects = new ComplexDocument[objects.length];
        for (int i = 0; i < objects.length; i++) {
            projectname = objects[i].getst("PROJECT_NAME");
            projects[i] = getDocument("WB_PROJECT", null, projectname);
        }
        
        return projects;
    }
    
//    private void printddojects(String ddobject, String project) {
//        Query query;
//        ExtendedObject[] objects;
//        
//        query = new Query();
//        query.setModel(ddobject);
//        query.andEqual("PROJECT", project);
//        objects = select(query);
//        if (objects != null)
//            for (ExtendedObject dtel : objects)
//                print(dtel.getst("NAME"));
//    }
}
