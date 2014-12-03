package org.iocaste.dataeditor;

import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.documents.common.ExtendedObject;

public class Context implements ExtendedContext {
    public ExtendedObject[] items;
    public String action, model;
}
