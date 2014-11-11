package org.iocaste.appbuilder;

import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.documents.common.ComplexDocument;

public class Context implements ExtendedContext {
    public Object id;
    public String redirect, number, cmodel; 
    public ComplexDocument document;
}
