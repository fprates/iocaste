package org.iocaste.workbench.project.datadict;

import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.ActionContext;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.viewer.ViewerItemUpdate;

public class ModelRemove extends AbstractCommand {
    
    public ModelRemove(Context extcontext) {
        super("model-remove", extcontext);
        ActionContext actionctx;
        
        required("name", "NAME");
        actionctx = getActionContext();
        actionctx.updateviewer =
                new ViewerItemUpdate(extcontext, "models_items");
    }
    
    @Override
    protected Object entry(PageBuilderContext context) {
        String name;
        ComplexDocument document;
        Context extcontext;
        Map<Object, ComplexDocument> items;
        
        name = getst("name");
        extcontext = getExtendedContext();
        document = extcontext.project.getDocumentsMap("model").get(name);
        if (document == null) {
            message(Const.ERROR, "invalid.model");
            return null;
        }

        items = document.getDocumentsMap("item");
        if ((items != null) && (items.size() > 0)) {
            message(Const.ERROR, "model.not.empty");
            return null;
        }
        
        extcontext.project.remove(document);
        delete(document);
        if (extcontext.model == document)
            extcontext.model = null;
        message(Const.STATUS, "model.removed", name);
        return null;
    }

}
