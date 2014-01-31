package org.iocaste.datadict;

import java.util.List;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.PageContext;

public class Context extends PageContext {
    public byte mode;
    public String modelname, shname;
    public DocumentModel model;
    public ExtendedObject header;
    public ExtendedObject[] shitens;
    public ItemDetail detail;
    public List<String> code;
    public String oldname;
    public Context context;
}