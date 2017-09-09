package org.iocaste.kernel.runtime.shell.renderer.internal;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.kernel.runtime.shell.elements.ViewState;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.AbstractEventHandler;
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
        EventHandler ehandler = events.get(event);
        
        ehandler.setView(state.viewctx.view);
        ehandler.onEvent(
                EventHandler.ON_FOCUS, parameters.get("action")[0]);
    }
}

class OnFocusEvent extends AbstractEventHandler {
	private static final long serialVersionUID = -5422757197316861242L;

	@Override
    public void onEvent(byte event, String args) {
        View view = getView();
        Element element = view.getElement(args);
        getView().setFocus(element);
    }
    
}
