package org.iocaste.datadict;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;

public class Context {
    public byte mode;
    public String modelname, shname;
    public DocumentModel model;
    public ExtendedObject header;
    public ExtendedObject[] shitens;
    public ItemDetail detail;
}
