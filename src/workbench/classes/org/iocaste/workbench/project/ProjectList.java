package org.iocaste.workbench.project;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;

public class ProjectList extends AbstractCommand {

    public ProjectList() {
        checkproject = false;
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Query query;
        ExtendedObject[] objects;
        ComplexDocument project;
        String projectname;
        
        query = new Query();
        query.setModel("WB_PROJECT_HEAD");
        objects = select(query);
        if (objects == null) {
            message(Const.ERROR, "no.project.available");
            return;
        }
        for (ExtendedObject object : objects) {
            projectname = object.getst("PROJECT_NAME");
            project = getDocument("project", projectname);
            print("- Nome: %s", object.getst("PROJECT_NAME"));
            
            print("- Perfil: %s", object.getst("PROFILE"));
            
            print("- Telas");
            for (ExtendedObject screen : project.getItems("screen"))
                print(screen.getst("SCREEN_NAME"));
            
            print("- Links");
            for (ExtendedObject link : project.getItems("link"))
                print(link.getst("NAME"));
            
            print("- Elementos de dados");
            printddojects("WB_DATA_ELEMENTS", projectname);
            
            print("- Modelos");
            printddojects("WB_MODEL_HEADER", projectname);
        }
    }
    
    private void printddojects(String ddobject, String project) {
        Query query;
        ExtendedObject[] objects;
        
        query = new Query();
        query.setModel(ddobject);
        query.andEqual("PROJECT", project);
        objects = select(query);
        if (objects != null)
        for (ExtendedObject dtel : objects)
            print(dtel.getst("NAME"));
    }
}
