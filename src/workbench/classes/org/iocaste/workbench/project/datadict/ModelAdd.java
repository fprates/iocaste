package org.iocaste.workbench.project.datadict;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class ModelAdd extends AbstractCommand {
    
    public ModelAdd(Context extcontext) {
        super("model-add", extcontext);
        required("name", "NAME");
        optional("table", "TABLE");
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
            message(Const.ERROR, "model %s already exists.", name);
            return null;
        }

        extcontext = getExtendedContext();
        document = extcontext.project.instance("model", name);
        object = document.getHeader();
        object.set("PROJECT", extcontext.project.getstKey());
        autoset(object);
        save(document);
        extcontext.project.add(document);
        print("model %s updated.", name);
        return document;
    }

}
