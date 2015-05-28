package org.iocaste.sh;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.iocaste.documents.common.ValueRange;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.SearchHelp;

public class Context extends AbstractContext {
    public Map<String, ValueRange> criteria;
    public SearchHelp control;
    public Properties messages;
    
    public Context() {
        criteria = new HashMap<>();
        messages = new Properties();
    }
}
