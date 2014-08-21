package org.iocaste.datadict;

import java.util.List;

import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;

public class Context implements ExtendedContext {
    public byte mode;
    public String modelname, shname;
    public DocumentModel model;
    public ExtendedObject head;
    public ExtendedObject[] items, shitems;
    public ItemDetail detail;
    public List<String> code;
    public String oldname;
}
