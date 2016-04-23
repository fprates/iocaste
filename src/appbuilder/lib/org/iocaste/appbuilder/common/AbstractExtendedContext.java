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
    
    @Override
    public final void add(String ttname, ExtendedObject object) {
        String pagename;
        TableToolContextEntry entry;
        
        pagename = context.view.getPageName();
        entry = pages.get(pagename).tabletools.get(ttname);
        entry.items.put(entry.items.size(), object);
    }
    
    @Override
    public final ExtendedObject dfobjectget(String dfname) {
        return dfobjectget(context.view.getPageName(), dfname);
    }
    
    @Override
    public final ExtendedObject dfobjectget(String page, String dfname) {
        PageContext pagectx;
        
        pagectx = pages.get(page);
        return pagectx.dataforms.get(dfname);
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
    
    @Override
    public final void set(String ttname, ExtendedObject[] objects) {
        set(context.view.getPageName(), ttname, objects);
    }

    @Override
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

    @Override
    public final void set(String dfname, ExtendedObject object) {
        set(context.view.getPageName(), dfname, object);
    }
    
    @Override
    public final void set(String page, String dfname, ExtendedObject object) {
        PageContext pagectx = pages.get(page);
        pagectx.dataforms.put(dfname, object);
    }
    
    @Override
    public final TableToolContextEntry tableInstance(String ttname) {
        return tableInstance(context.view.getPageName(), ttname);
    }
    
    @Override
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
