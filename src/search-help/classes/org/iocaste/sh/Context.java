package org.iocaste.sh;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.ValueRange;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.SearchHelp;

public class Context extends AbstractContext {
    public Map<String, ValueRange> criteria;
    public SearchHelp control;
    public MessageSource source;
    
    public Context() {
        criteria = new HashMap<>();
        source = new MessageSource();
    }
}
