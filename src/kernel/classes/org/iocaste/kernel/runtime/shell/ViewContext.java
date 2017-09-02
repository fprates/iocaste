package org.iocaste.kernel.runtime.shell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.runtime.common.application.ToolData;
import org.iocaste.runtime.common.application.ViewExport;
import org.iocaste.runtime.common.page.ViewSpecItem.TYPES;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.View;
import org.iocaste.shell.common.ViewTitle;
import org.iocaste.kernel.runtime.RuntimeEngine;
import org.iocaste.kernel.runtime.shell.renderer.internal.ActionEventHandler;
import org.iocaste.kernel.runtime.shell.renderer.internal.TrackingData;

public class ViewContext {
    public View view;
    public RuntimeEngine function;
    public TrackingData tracking;
    public ViewTitle title;
    public Map<String, ComponentEntry> entries;
    public Map<Const, TYPES> types;
    public MessageSource messagesrc;
    public Const messagetype;
    public String messagetext, sessionid, locale;
    public Object[] messageargs;
    public List<String> inputs;
    public Map<String, Map<String, Map<String, ActionEventHandler>>> actions;
    public Map<String, ViewExport> subpages;
    public ComponentEntry parent;
    public boolean offline, noeventhandlers;
	
    public ViewContext() {
        this(new View(null, null));
    }
    
    public ViewContext(View view) {
        this.view = view;
        tracking = new TrackingData();
        entries = new LinkedHashMap<>();
        inputs = new ArrayList<>();
        actions = new HashMap<>();
        subpages = new HashMap<>();
    }
    
    public final void add(ToolData data) {
        ComponentEntry entry = new ComponentEntry();
        entry.data = data;
        entries.put(data.name, entry);
    }
    
    public final ActionEventHandler addEventHandler(
            String element, String action, String event) {
        ActionEventHandler handler;
        Map<String, ActionEventHandler> handlers;
        Map<String, Map<String, ActionEventHandler>> elementhandlers;
        
        elementhandlers = actions.get(element);
        if (elementhandlers == null)
            actions.put(element, elementhandlers = new HashMap<>());
        handlers = elementhandlers.get(action);
        if (handlers == null)
            elementhandlers.put(action, handlers = new LinkedHashMap<>());
    
        handler = new ActionEventHandler();
        handler.event = (event.equals(""))? null : event;
        handlers.put(event, handler);
        return handler;
    }
    
    public final ActionEventHandler getEventHandler(
            String element, String action, String event) {
    	return actions.get(element).get(action).get(event);
    }
    
    public final ToolData instance(TYPES type, String name) {
        ToolData tooldata = new ToolData(type, name);
        add(tooldata);
        return tooldata;
    }
}