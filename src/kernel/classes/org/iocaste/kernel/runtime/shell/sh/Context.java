package org.iocaste.kernel.runtime.shell.sh;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ValueRange;
import org.iocaste.kernel.runtime.shell.PopupData;
import org.iocaste.shell.common.SearchHelp;

public class Context {
    public PopupData popup;
    public Map<String, ValueRange> criteria;
    public DocumentModel model;
    public SearchHelp control;
    
    public Context() {
        criteria = new HashMap<>();
    }
}
