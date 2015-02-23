package org.iocaste.internal;

import java.util.Map;

import org.iocaste.protocol.Function;
import org.iocaste.shell.common.PopupControl;
import org.iocaste.shell.common.ViewState;

public class ControllerData {
    public ViewState state;
    public Map<String, ?> values;
    public PageContext pagectx;
    public Function function;
    public String sessionid, contextname, servername, contexturl;
    public int logid;
    public boolean event;
    public PopupControl popupcontrol;
    
    public ControllerData() {
        state = new ViewState();
    }
}
