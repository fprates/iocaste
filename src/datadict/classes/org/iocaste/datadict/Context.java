package org.iocaste.datadict;

import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;

public class Context implements ExtendedContext {
    public String modelname;
    public DocumentModel model;
    public ExtendedObject head;
    public ExtendedObject[] items;
}
