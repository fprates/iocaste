package org.iocaste.workbench.project.view;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
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
        ExtendedObject view;
        String name, project;
        Context extcontext = getExtendedContext();
        
        name = parameters.get("name");
        project = extcontext.project.getstKey();
        view = getView(extcontext.project, name);
        if (view != null) {
            message(Const.ERROR, "view.already.exists");
            return;
        }
        
        view = instance("WB_PROJECT_SCREENS");
        view.set("SCREEN_NAME", name);
        view.set("PROJECT", project);
        extcontext.project.add(view);
        save("project", extcontext.project);
        extcontext.output.add(String.format("view %s created.", name));
    }

    public static final ExtendedObject getView(
            ComplexDocument project, String name) {
        ExtendedObject[] objects;
        Map<String, Object> criteria;
        
        objects = project.getItems("screen");
        if ((objects == null) || (objects.length == 0))
            return null;
        criteria = new HashMap<>();
        criteria.put("SCREEN_NAME", name);
        criteria.put("PROJECT", project.getstKey());
        return readobjects(objects, criteria);
    }
}
