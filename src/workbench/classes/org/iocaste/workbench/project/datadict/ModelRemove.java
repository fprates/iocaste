package org.iocaste.workbench.project.datadict;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class ModelRemove extends AbstractCommand {
    
    public ModelRemove() {
        required("name");
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        String name;
        ComplexDocument document;
        Context extcontext;
        
        name = parameters.get("name");
        extcontext = getExtendedContext();
        document = extcontext.project.getDocumentsMap("model").get(name);
        if (document == null) {
            message(Const.ERROR, "invalid model %s.", name);
            return;
        }

        extcontext.project.remove(document);
        delete(document);
        if (extcontext.model == document)
            extcontext.model = null;
        print("model %s removed.", name);
    }

}
