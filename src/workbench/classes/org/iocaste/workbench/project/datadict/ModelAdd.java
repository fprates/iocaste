package org.iocaste.workbench.project.datadict;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.ActionContext;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.viewer.ViewerItemUpdate;

public class ModelAdd extends AbstractCommand {
    
    public ModelAdd(Context extcontext) {
        super("model-add", extcontext);
        ActionContext actionctx;
        
        required("name", "NAME");
        optional("table", "TABLE");
        
        actionctx = getActionContext();
        actionctx.updateviewer = new ViewerItemUpdate("models_items");
    }
    
    @Override
    protected Object entry(PageBuilderContext context) {
        String name;
        ComplexDocument document;
        ExtendedObject object;
        Context extcontext;
        
        name = parameters.get("name");
        document = getDocument("WB_MODELS", name);
        if (document != null) {
            message(Const.ERROR, "model.exists");
            return null;
        }

        extcontext = getExtendedContext();
        document = extcontext.project.instance("model", name);
        object = document.getHeader();
        object.set("PROJECT", extcontext.project.getstKey());
        autoset(object);
        save(document);
        extcontext.project.add(document);
        message(Const.STATUS, "model.updated");
        return document.getHeader();
    }

}
