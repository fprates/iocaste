package org.iocaste.workbench.project.datadict;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class ModelAdd extends AbstractCommand {
    
    public ModelAdd() {
        required("name");
        optional("table");
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        String name;
        ComplexDocument document;
        ExtendedObject object;
        Context extcontext;
        
        name = parameters.get("name");
        document = getDocument("WB_MODELS", name);
        if (document != null) {
            message(Const.ERROR, "model %s already exists.", name);
            return;
        }

        extcontext = getExtendedContext();
        document = extcontext.project.instance("model");
        object = document.getHeader();
        object.set("NAME", name);
        object.set("PROJECT", extcontext.project.getstKey());
        object.set("TABLE", parameters.get("table"));
        save(document);
        extcontext.project.add(document);
        print("model %s updated.", name);
    }

}
