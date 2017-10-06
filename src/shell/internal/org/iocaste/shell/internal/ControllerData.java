package org.iocaste.shell.internal;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.protocol.Function;
import org.iocaste.shell.common.AbstractEventHandler;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.EventHandler;
import org.iocaste.shell.common.PopupControl;
import org.iocaste.shell.common.View;
import org.iocaste.shell.common.ViewState;

public class ControllerData {
    public ViewState state;
    public Map<String, ?> values;
    public PageContext pagectx;
    public Function function;
    public String sessionid, contextname, servername;
    public int logid;
    public boolean event, disconnecteddb;
    public PopupControl popupcontrol;
    private Map<String, EventHandler> events;
    
    public ControllerData() {
        state = new ViewState();
        events = new HashMap<>();
        events.put("onfocus", new OnFocusEvent());
    }
    
    public final void execonevent(Map<String, String[]> parameters) {
        String event = parameters.get("event")[0];
        OnFocusEvent ehandler = (OnFocusEvent)events.get(event);
        
        ehandler.setView(state.view);
        ehandler.onEvent(parameters.get("action")[0]);
    }
}

class OnFocusEvent extends AbstractEventHandler {
    private static final long serialVersionUID = 656542479738559096L;

    public void onEvent(String action) {
        View view = getView();
        Element element = view.getElement(action);
        getView().setFocus(element);
    }

    @Override
    public void onEvent(ControlComponent control) {
        onEvent(control.getAction());
    }
    
}
