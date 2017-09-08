package org.iocaste.runtime.common.managedview.edit;

import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.runtime.common.application.Context;

public class ConfigData {
    public static final byte UPDATE = 1;
    public static final byte DISPLAY = 2;
    public DocumentModelItem hkey;
    public ComplexModel cmodel;
    public byte mode;
    public boolean mark;
    public Context context;
}
