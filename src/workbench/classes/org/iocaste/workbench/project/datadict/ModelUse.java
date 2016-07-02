package org.iocaste.workbench.project.datadict;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class ModelUse extends AbstractCommand {
    
    public ModelUse(Context extcontext) {
        super("model-use", extcontext);
        required("name", null);
    }
    
    @Override
    protected Object entry(PageBuilderContext context) {
        String name;
        ComplexDocument document;
        Context extcontext = getExtendedContext();

        name = parameters.get("name");
        document = extcontext.project.getDocumentsMap("model").get(name);
        if (document == null) {
            message(Const.ERROR, "model %s undefined.", name);
            return null;
        }
        
        extcontext.model = document;
        print("using model %s.", name);
        return document;
    }

}
