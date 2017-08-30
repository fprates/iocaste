package org.iocaste.runtime.common.application;

import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.runtime.common.ActionHandler;
import org.iocaste.runtime.common.RuntimeEngine;
import org.iocaste.runtime.common.managedview.ManagedViewContext;
import org.iocaste.runtime.common.page.AbstractPage;
import org.iocaste.runtime.common.portal.PortalContext;
import org.iocaste.shell.common.MessageSource;

public interface Context {

    public abstract void add(String name, AbstractPage page);
    
    public abstract ActionHandler getHandler(String action);
    
    public abstract String getAppName();
    
    public abstract Map<String, ManagedViewContext> getEntities();
    
    public abstract MessageSource getMessageSource();

    public abstract ExtendedObject get(
            String page, String tooldata, int index);
    
    public abstract Set<String> getManagedViews();
    
    public abstract String getPageName();
    
    public abstract AbstractPage getPage();
    
    public abstract AbstractPage getPage(String name);
    
    public abstract Map<String, AbstractPage> getPages();
    
    public abstract ManagedViewContext mviewctx(String entity);
    
    public abstract ManagedViewContext mviewctx();
    
    public abstract void popPage();
    
    public abstract void popPage(String name);
    
    public abstract PortalContext portalctx();
    
    public abstract void pushPage();
    
    public abstract RuntimeEngine runtime();
    
    public abstract void set(Application<?> application);
    
    public abstract void set(RuntimeEngine iocaste);
    
    public abstract void set(String tooldata, ExtendedObject object);
    
    public abstract void set(
    		String page, String tooldata, ExtendedObject object);
    
    public abstract void set(
            String page, String tooldata, ExtendedObject[] object);
    
    public abstract void set(MessageSource messagesrc);
    
    public abstract void setPageName(String name);
}

