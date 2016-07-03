package org.iocaste.workbench;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;

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
        
        dataelementsLoad(extcontext, project);
        
        parameters.clear();
        parameters.put("name", project);
        command = context.getView().getActionHandler("project-use");
        command.set(parameters);
        command.run(context);
        init("project_viewer", extcontext);
        redirect("project_viewer");
    }
    
}