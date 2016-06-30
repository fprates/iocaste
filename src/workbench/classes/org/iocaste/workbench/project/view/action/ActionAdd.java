package org.iocaste.workbench.project.view.action;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class ActionAdd extends AbstractCommand {
    private Map<String, Integer> types;
    
    public ActionAdd() {
        required("name");
        required("class");
        optional("type");
        checkview = true;
        types = new HashMap<>();
        types.put("action", 0);
        types.put("submit", 1);
        types.put("put", 2);
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        ComplexDocument classes;
        String name, classname, typename;
        String[] tokens;
        ExtendedObject object;
        StringBuilder packagename;
        int type;
        Context extcontext = getExtendedContext();
        
        name = parameters.get("name");
        object = extcontext.view.getItemsMap("action").get(name);
        if (object != null) {
            message(Const.ERROR, "action.already.exists");
            return;
        }
        
        classname = parameters.get("class");
        packagename = null;
        tokens = classname.split("\\.");
        for (int i = 0; i < tokens.length - 1; i++)
            if (packagename == null)
                packagename = new StringBuilder(tokens[i]);
            else
                packagename.append(".").append(tokens[i]);
        
        classes = extcontext.project.getDocumentsMap("class").
                get(packagename.toString());
        if (classes == null) {
            message(Const.ERROR, "invalid.class");
            return;
        }
        
        if (classes.getItemsMap("class").get(classname) == null) {
            message(Const.ERROR, "invalid.class");
            return;
        }
        
        if (parameters.containsKey("type")) {
            typename = parameters.get("type");
            if (!types.containsKey(typename)) {
                message(Const.ERROR, "invalid.type");
                return;
            }
            type = types.get(typename);
        } else {
            type = 0;
        }
        
        object = extcontext.view.instance("action", name);
        object.set("PROJECT", extcontext.project.getstKey());
        object.set("SCREEN", extcontext.view.getstKey());
        object.set("NAME", name);
        object.set("CLASS", classname);
        object.set("TYPE", type);
        save(extcontext.view);
        print("action %s added.", name);
    }
}