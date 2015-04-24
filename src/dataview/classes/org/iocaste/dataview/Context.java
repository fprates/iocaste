package org.iocaste.dataview;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.AbstractContext;

public class Context extends AbstractContext {
    public Documents documents;
    public DocumentModelItem nsitem;
    public DocumentModel modelmodel, model;
    public ExtendedObject[] items;
}
