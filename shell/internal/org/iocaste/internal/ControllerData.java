package org.iocaste.internal;

import java.util.Map;

import org.iocaste.protocol.Function;
import org.iocaste.shell.common.View;

public class ControllerData {
    public View view;
    public Map<String, ?> values;
    public Function function;
    public String sessionid, contextname, servername;
    public int logid;
    public boolean event;
}
