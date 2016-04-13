package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.cmodelviewer.TableToolContextEntry;
import org.iocaste.documents.common.ExtendedObject;

public abstract class AbstractExtendedContext implements ExtendedContext {
    private PageBuilderContext context;
    private Map<String, PageContext> pages;

    public AbstractExtendedContext(PageBuilderContext context) {
        this.context = context;
        pages = new HashMap<>();
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
    
    @Override
    public final void pageInstance() {
        pageInstance(context.view.getPageName());
    }
    
    @Override
    public final void pageInstance(String page) {
        if (!pages.containsKey(page))
            pages.put(page, new PageContext());
    }
    
    public final void set(String ttname, ExtendedObject[] objects) {
        set(context.view.getPageName(), ttname, objects);
    }

    public final void set(String page, String ttname, ExtendedObject[] objects)
    {
        TableToolContextEntry entry;

        entry = pages.get(page).tabletools.get(ttname);
        entry.items.clear();
        if (objects == null)
            return;
        for (int i = 0; i < objects.length; i++)
            entry.items.put(i, objects[i]);
    }
    
    public final TableToolContextEntry tableInstance(String ttname) {
        return tableInstance(context.view.getPageName(), ttname);
    }
    
    public final TableToolContextEntry tableInstance(String page, String ttname)
    {
        TableToolContextEntry entry;
        PageContext pagectx;
        
        pagectx = pages.get(page);
        entry = pagectx.tabletools.get(ttname);
        if (entry == null) {
            entry = new TableToolContextEntry();
            pagectx.tabletools.put(ttname, entry);
        }
        return entry;
    }
}
