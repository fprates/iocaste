package org.iocaste.appbuilder.common.cmodelviewer;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AppBuilderLink;
import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;

public class Context implements ExtendedContext {
    private PageBuilderContext context;
    public Object id, ns;
    public String redirect; 
    public ComplexDocument document;
    public AppBuilderLink link;
    public Map<String, PageContext> pages;
    
    public Context(PageBuilderContext context) {
        pages = new HashMap<>();
        this.context = context;
    }
    
    public final void add(String ttname, ExtendedObject object) {
        String pagename;
        TableToolContextEntry entry;
        
        pagename = context.view.getPageName();
        entry = pages.get(pagename).tabletools.get(ttname);
        entry.items.put(entry.items.size(), object);
    }
    
    public final PageContext getPageContext() {
        String pagename = context.view.getPageName();
        return pages.get(pagename);
    }
    
    public final void pageInstance() {
        String pagename = context.view.getPageName();
        pages.put(pagename, new PageContext());
    }
    
    public final void set(String ttname, ExtendedObject[] objects) {
        String pagename;
        TableToolContextEntry entry;

        pagename = context.view.getPageName();
        entry = pages.get(pagename).tabletools.get(ttname);
        entry.items.clear();
        if (objects == null)
            return;
        for (int i = 0; i < objects.length; i++)
            entry.items.put(i, objects[i]);
    }
    
    public final TableToolContextEntry tableInstance(String ttname) {
        String pagename = context.view.getPageName();
        TableToolContextEntry entry = new TableToolContextEntry();
        pages.get(pagename).tabletools.put(ttname, entry);
        return entry;
    }
}
