package org.iocaste.internal;

import java.util.Map;
import java.util.Set;

import org.iocaste.protocol.Function;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.View;

public class ControllerData {
    public View view;
    public Map<String, ?> values;
    public Function function;
    public String sessionid, contextname, servername, contexturl;
    public int logid;
    public boolean event;
    public ControlComponent shcontrol;
    public Set<String> customs;
}
