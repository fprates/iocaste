package org.iocaste.kernel.runtime.shell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.runtime.common.application.ToolData;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.View;
import org.iocaste.shell.common.ViewTitle;
import org.iocaste.kernel.runtime.Runtime;
import org.iocaste.kernel.runtime.shell.renderer.internal.ActionEventHandler;
import org.iocaste.kernel.runtime.shell.renderer.internal.TrackingData;

public class ViewContext {
	public View view;
	public Runtime function;
	public TrackingData tracking;
	public ViewTitle title;
	public Map<String, ComponentEntry> entries;
	public MessageSource messagesrc;
    public Const messagetype;
    public String messagetext; 
    public Object[] messageargs;
    public List<String> inputs;
    public Map<String, Map<String, ActionEventHandler>> actions;
	
	public ViewContext() {
		view = new View(null, null);
		tracking = new TrackingData();
		entries = new LinkedHashMap<>();
	    inputs = new ArrayList<>();
	    actions = new HashMap<>();
	}
	
	public final void add(ToolData data) {
		ComponentEntry entry = new ComponentEntry();
		entry.data = data;
		entries.put(data.name, entry);
	}
    
    public final ActionEventHandler addEventHandler(String event, String action)
    {
        ActionEventHandler handler;
        Map<String, ActionEventHandler> handlers;
        
        handlers = actions.get(action);
        if (handlers == null) {
            handlers = new LinkedHashMap<>();
            actions.put(action, handlers);
        }

        handler = new ActionEventHandler();
        handler.event = event;
        handlers.put(event, handler);
        return handler;
    }
	
	public final ActionEventHandler getEventHandler(String action, String event)
	{
		return actions.get(action).get(event);
	}
}
