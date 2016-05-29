package org.iocaste.workbench.project.view;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class ViewAdd extends AbstractCommand {

    public ViewAdd() {
        required("name");
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        ExtendedObject[] objects;
        ExtendedObject view;
        String name, project;
        Map<String, Object> criteria;
        Context extcontext = getExtendedContext();
        
        if (extcontext.project == null) {
            message(Const.ERROR, "undefined.project");
            return;
        }
        
        name = parameters.get("name");
        project = extcontext.project.getstKey();
        objects = extcontext.project.getItems("screen");
        if ((objects != null) && (objects.length > 0)) {
            criteria = new HashMap<>();
            criteria.put("PROJECT_SCREEN", name);
            criteria.put("PROJECT", project);
            view = readobjects(objects, criteria);
            if (view != null) {
                message(Const.ERROR, "view.already.exists");
                return;
            }
        }
        
        view = instance("WB_PROJECT_SCREENS");
        view.set("SCREEN_NAME", name);
        view.set("PROJECT", project);
        extcontext.project.add(view);
        save("project", extcontext.project);
        extcontext.output.add(String.format("view %s created.", name));
    }

}
