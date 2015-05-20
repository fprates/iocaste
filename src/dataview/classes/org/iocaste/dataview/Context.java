package org.iocaste.dataview;

import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;

public class Context implements ExtendedContext {
    public Documents documents;
    public DocumentModelItem nsitem;
    public DocumentModel modelmodel, model;
    public ExtendedObject[] items;
    
    public Context(PageBuilderContext context) {
        documents = new Documents(context.function);
        modelmodel = documents.getModel("MODEL");
    }
}
