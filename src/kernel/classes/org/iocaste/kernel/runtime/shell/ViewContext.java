package org.iocaste.kernel.runtime.shell;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.runtime.common.application.ToolData;
import org.iocaste.runtime.common.application.ViewExport;
import org.iocaste.runtime.common.page.ViewSpecItem.TYPES;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.View;
import org.iocaste.kernel.runtime.RuntimeEngine;
import org.iocaste.kernel.runtime.shell.renderer.internal.ActionEventHandler;

public class ViewContext {
    public View view;
    public ViewExport viewexport;
    public RuntimeEngine function;
    public Map<String, ComponentEntry> entries;
    public Map<Const, TYPES> types;
    public MessageSource messagesrc;
    public Const messagetype;
    public String messagetext, sessionid, locale;
    public Object[] messageargs;
    public Map<String, Map<String, Map<String, ActionEventHandler>>> actions;
    public Map<String, ViewExport> subpages;
    public boolean offline, noeventhandlers;
	
    public ViewContext() {
        this(new View(null, null));
    }
    
    public ViewContext(View view) {
        this.view = view;
        entries = new LinkedHashMap<>();
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