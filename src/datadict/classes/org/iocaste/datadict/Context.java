package org.iocaste.datadict;

import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;

public class Context implements ExtendedContext {
    public DocumentModel model;
    public ExtendedObject head;
    public ExtendedObject[] items;
    
    public Context(PageBuilderContext context) {
        model = new Documents(context.function).getModel("MODEL");
    }
}
