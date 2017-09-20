package org.iocaste.kernel.runtime.shell.renderer.internal;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.kernel.runtime.shell.ViewState;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.AbstractEventHandler;
import org.iocaste.shell.common.ControlComponent;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.EventHandler;
import org.iocaste.shell.common.View;

public class ControllerData {
    public ViewState state;
    public Map<String, Object> values;
    public Function function;
    public String contextname, contexturl;
    public boolean event;
    private Map<String, EventHandler> events;
    
    public ControllerData() {
        state = new ViewState();
        events = new HashMap<>();
        events.put("onfocus", new OnFocusEvent());
        values = new HashMap<>();
    }
    
    public final void execonevent(Map<String, String[]> parameters) {
        String event = parameters.get("event")[0];
        OnFocusEvent ehandler = (OnFocusEvent)events.get(event);
        
        ehandler.setView(state.viewctx.view);
        ehandler.onEvent(parameters.get("action")[0]);
    }
}

class OnFocusEvent extends AbstractEventHandler {
	private static final long serialVersionUID = -5422757197316861242L;

	public void onEvent(String action) {
        View view = getView();
        Element element = view.getElement(action);
        getView().setFocus(element);
	}
	
	@Override
    public void onEvent(byte event, ControlComponent control) {
	    onEvent(control.getAction());
    }
    
}
