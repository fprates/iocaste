package org.iocaste.runtime.common.application;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.IocasteException;
import org.iocaste.runtime.common.ActionHandler;
import org.iocaste.runtime.common.RuntimeEngine;
import org.iocaste.runtime.common.managedview.ManagedViewContext;
import org.iocaste.runtime.common.page.AbstractPage;
import org.iocaste.runtime.common.page.StandardPage;
import org.iocaste.runtime.common.portal.PortalContext;
import org.iocaste.shell.common.MessageSource;

public abstract class AbstractContext implements Context {
    private PortalContext portalctx;
    private Map<String, ManagedViewContext> entities;
    private MessageSource messagesrc;
    private Map<String, AbstractPage> pages;
    private String page, appname;
    private RuntimeEngine runtime;
    private Stack<String> pagestack;
    private Locale locale;
    
    public AbstractContext() {
        pages = new HashMap<>();
        page = "main";
        pagestack = new Stack<>();
        portalctx = new PortalContext(this);
        entities = new HashMap<>();
    }
    
    @Override
    public final void add(String name, AbstractPage page) {
        pages.put(name, new StandardPage(page));
    }
    
    public final void add(String tooldata, ExtendedObject object) {
        add(page, tooldata, object);
    }
    
    public final void add(
            String page, String tooldata, ExtendedObject object) {
        Map<Integer, ExtendedObject> objects = pages.get(page).
                instance(tooldata).objects;
        objects.put(objects.size(), object);
    }
    
    @Override
    public final String getAppName() {
    	return appname;
    }
    
    @Override
    public final Map<String, ManagedViewContext> getEntities() {
        return entities;
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
    public final ExtendedObject get(String page, String tooldata, int index) {
        return pages.get(page).instance(tooldata).objects.get(index);
    }

    @Override
    public final Locale getLocale() {
        return locale;
    }
    
    @Override
    public final Set<String> getManagedViews() {
        return entities.keySet();
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
    public final ManagedViewContext mviewctx(String entity) {
        ManagedViewContext mviewctx = entities.get(entity);
        if (mviewctx == null)
            entities.put(entity, mviewctx = new ManagedViewContext(entity));
        return mviewctx;
    }
    
    @Override
    public final ManagedViewContext mviewctx() {
        return mviewctx(getPage().getEntity());
    }
    
    @Override
    public final void popPage() {
        if (pagestack.size() > 0)
            page = pagestack.pop();
    }
    
    @Override
    public final void popPage(String name) {
        Iterator<String> it;
        
        if (name == null) {
            if (pagestack.size() > 0) {
                page = pagestack.get(0);
                pagestack.clear();
            }
            return;
        }
        
        it = pagestack.iterator();
        while (it.hasNext()) {
            page = pagestack.pop();
            if (it.next().equals(name))
                return;
        }
    }

    @Override
    public final PortalContext portalctx() {
        return portalctx;
    }
    
    @Override
    public final void pushPage() {
        pagestack.push(page);
    }
    
    @Override
    public final RuntimeEngine runtime() {
        return runtime;
    }
    
    @Override
    public final void set(Application<?> application) {
    	appname = application.getAppName();
    }
    
    @Override
    public final void set(String tooldata, ExtendedObject object) {
    	set(page, tooldata, object);
    }
    
    @Override
    public final void set(String page, String tooldata, ExtendedObject object) {
    	pages.get(page).instance(tooldata).object = object;
    }
    
    @Override
    public final void set(String tooldata, ExtendedObject[] objects) {
        set(page, tooldata, objects);
    }

    @Override
    public final void set(
            String page, String tooldata, ExtendedObject[] objects) {
        AbstractPage _page = pages.get(page);
        for (int i = 0; i < objects.length; i++)
            _page.instance(tooldata).objects.put(i, objects[i]);
    }
    
    @Override
    public final void set(
            String tooldata, Collection<ExtendedObject> objects) {
        set(page, tooldata, objects);
    }
    
    @Override
    public final void set(
            String page, String tooldata, Collection<ExtendedObject> objects) {
        AbstractPage _page = pages.get(page);
        int i = 0;
        for (ExtendedObject object : objects)
            _page.instance(tooldata).objects.put(i++, object);
    }
    
    @Override
    public final void set(MessageSource messagesrc) {
    	this.messagesrc = messagesrc;
    }
    
    @Override
    public final void set(RuntimeEngine iocaste) {
    	this.runtime = iocaste;
    }
    
    @Override
    public final void set(Locale locale) {
        this.locale = locale;
    }
    
    @Override
    public final void setPageName(String name) {
        if (name == null)
            throw new IocasteException("can't set current page to null.");
        this.page = name;
    }
}