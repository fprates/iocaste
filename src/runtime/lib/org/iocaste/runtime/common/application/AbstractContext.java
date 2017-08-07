package org.iocaste.runtime.common.application;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.runtime.common.ActionHandler;
import org.iocaste.runtime.common.RuntimeEngine;
import org.iocaste.runtime.common.page.AbstractPage;
import org.iocaste.runtime.common.page.StandardPage;
import org.iocaste.shell.common.MessageSource;

public abstract class AbstractContext implements Context {
    private MessageSource messagesrc;
    private Map<String, AbstractPage> pages;
    private String page, appname;
    private RuntimeEngine runtime;
    
    public AbstractContext() {
        pages = new HashMap<>();
        page = "main";
    }
    
    public final void add(String name, AbstractPage page) {
        pages.put(name, new StandardPage(page));
    }
    
    public final void add(String tooldata, ExtendedObject object) {
        add(page, tooldata, object);
    }
    
    public final void add(
            String page, String tooldata, ExtendedObject object) {
        Map<Integer, ExtendedObject> objects = pages.get(page).
                getToolData(tooldata).objects;
        objects.put(objects.size(), object);
    }
    
    @Override
    public final String getAppName() {
    	return appname;
    }
    
    @Override
    public final ActionHandler getHandler(String action) {
    	AbstractPage page = getPage();
    	ActionHandler handler = page.getActionHandler(action);
    	if (handler != null)
    		return handler;
    	for (String key : page.getChildren())
    		if ((handler = page.getChild(key).getActionHandler(action)) != null)
    			return handler;
    	return null;
    }
    
    @Override
    public final MessageSource getMessageSource() {
    	return messagesrc;
    }
    
    @Override
    public final AbstractPage getPage() {
    	return getPage(page);
    }
    
    @Override
    public final AbstractPage getPage(String name) {
    	return pages.get(name);
    }
    
    @Override
    public final String getPageName() {
    	return page;
    }
    
    @Override
    public final Map<String, AbstractPage> getPages() {
    	return pages;
    }
    
    @Override
    public final RuntimeEngine runtime() {
        return runtime;
    }
    
    public final void set(Application<?> application) {
    	appname = application.getAppName();
    }
    
    @Override
    public final void set(String tooldata, ExtendedObject object) {
    	set(page, tooldata, object);
    }
    
    @Override
    public final void set(String page, String tooldata, ExtendedObject object) {
    	pages.get(page).getToolData(tooldata).object = object;
    }
    
    public final void set(MessageSource messagesrc) {
    	this.messagesrc = messagesrc;
    }
    
    @Override
    public final void setPageName(String name) {
    	this.page = name;
    }
    
    @Override
    public final void set(RuntimeEngine iocaste) {
    	this.runtime = iocaste;
    }
}