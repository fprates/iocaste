package org.iocaste.datadict;

import org.iocaste.appbuilder.common.AbstractExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;

public class Context extends AbstractExtendedContext {
    public DocumentModel model;
    
    public Context(PageBuilderContext context) {
        super(context);
        model = new Documents(context.function).getModel("MODEL");
    }
}
