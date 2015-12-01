package org.iocaste.appbuilder.common.dataformtool;

import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.shell.common.Const;

public class DataFormToolItem {
    public String name, sh;
    public Const componenttype;
    public boolean secret, focus, required, invisible, disabled;
    public Map<String, Object> values;
    public DataElement element;
}
