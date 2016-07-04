package org.iocaste.workbench.project;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class ProjectSelect extends AbstractActionHandler {
    private Map<String, String> parameters;
    
    public ProjectSelect() {
        parameters = new HashMap<>();
    }
    
    private final void dataelementsLoad(Context extcontext, String project) {
        ExtendedObject[] objects;
        Query query = new Query();
        
        query.setModel("WB_DATA_ELEMENTS");
        query.andEqual("PROJECT", project);
        objects = select(query);
        extcontext.tableInstance("project_viewer", "data_elements_items");
        extcontext.set("project_viewer", "data_elements_items", objects);
    }
    
    private final void modelsLoad(Context extcontext) {
        Map<Object, ComplexDocument> models;
        ExtendedObject[] objects;
        int size;
        
        models = extcontext.project.getDocumentsMap("model");
        if ((models == null) || ((size = models.size()) == 0))
            return;
        
        objects = new ExtendedObject[size];
        size = 0;
        for (Object key : models.keySet())
            objects[size++] = models.get(key).getHeader();
        extcontext.tableInstance("project_viewer", "models_items");
        extcontext.set("project_viewer", "models_items", objects);
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        AbstractCommand command;
        Context extcontext = getExtendedContext();
        String project = getinputst("item_PROJECT");
        
        if (project.equals("project_add")) {
            init("project_add", extcontext);
            redirect("project_add");
            return;
        }
        
        parameters.clear();
        parameters.put("name", project);
        command = context.getView().getActionHandler("project-use");
        command.set(parameters);
        command.call(context);
        
        dataelementsLoad(extcontext, project);
        modelsLoad(extcontext);
        
        init("project_viewer", extcontext);
        redirect("project_viewer");
    }
    
}