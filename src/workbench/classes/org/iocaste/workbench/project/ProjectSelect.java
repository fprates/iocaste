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
    private Map<String, Object> parameters;
    
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
        
        parameters.clear();
        parameters.put("name", project);
        command = context.getView().getActionHandler("project-use");
        command.set(parameters);
        command.call(context);
        
        dataelementsLoad(extcontext, project);
        documentsLoad("models_items", "model", extcontext);
        documentsLoad("views_items", "screen", extcontext);
        linksLoad(extcontext);
        documentsLoad("packages_items", "class", extcontext);
        
        init("project_viewer", extcontext);
        redirect("project_viewer");
    }
    
    private final void linksLoad(Context extcontext) {
        Map<Object, ExtendedObject> items;

        extcontext.tableInstance(
                "project_viewer", "links_items").items.clear();
        items = extcontext.project.getItemsMap("link");
        if ((items == null) || (items.size() == 0))
            return;
        
        for (Object key : items.keySet())
            extcontext.add("project_viewer", "links_items", items.get(key));
    }
    
    private final void documentsLoad(
            String table, String item, Context extcontext) {
        Map<Object, ComplexDocument> models;

        extcontext.tableInstance("project_viewer", table).items.clear();
        models = extcontext.project.getDocumentsMap(item);
        if ((models == null) || (models.size() == 0))
            return;
        
        for (Object key : models.keySet())
            extcontext.add(
                    "project_viewer", table, models.get(key).getHeader());
    }
}