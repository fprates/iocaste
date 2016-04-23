package org.iocaste.workbench.project;

import java.util.Collection;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageContext;
import org.iocaste.appbuilder.common.cmodelviewer.Context;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.WorkbenchValidate;

public class ModelAdd extends WorkbenchValidate {

    @Override
    protected void execute(PageBuilderContext context) {
//        String name;
//        Context extcontext;
//        Documents documents;
//        ExtendedObject object;
//        Collection<ExtendedObject> objects;
//        PageContext page;
//        
//        super.execute(context);
//        name = getinputst("model_name");
//        if (name == null) {
//            message(Const.ERROR, "model.name.required");
//            setFocus("model_name");
//            return;
//        }
//
//        extcontext = getExtendedContext();
//        page = extcontext.getPageContext();
//        objects = page.tabletools.get("model_table").items.values();
//        if (Documents.readobjects(objects, "MODEL_NAME", name) != null) {
//            message(Const.ERROR, "model.already.added");
//            setFocus("model_name");
//            return;
//        }
//        
//        documents = new Documents(context.function);
//        if (documents.getModel(name) != null) {
//            message(Const.ERROR, "model.exists");
//            setFocus("model_name");
//            return;
//        }
//        
//        object = instance("WB_PROJECT_MODELS");
//        object.set("MODEL_NAME", name);
//        extcontext.add("model_table", object);
    }

}
