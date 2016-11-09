package org.iocaste.upload;

import org.iocaste.appbuilder.common.AbstractExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;

public class Context extends AbstractExtendedContext {
    public Object ns;
    public DocumentModelItem nsitem;
    public DocumentModel model;
    public ExtendedObject options;
    public ComplexDocument layout;
    public String linemodel;
    public byte[] content;
    
    public Context(PageBuilderContext context) {
        super(context);
    }
}
