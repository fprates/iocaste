package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.cmodelviewer.TableToolContextEntry;
import org.iocaste.appbuilder.common.tabletool.TableToolItem;
import org.iocaste.appbuilder.common.tiles.Tile;
import org.iocaste.documents.common.ExtendedObject;

public abstract class AbstractExtendedContext implements ExtendedContext {
    private PageBuilderContext context;
    private Map<String, PageContext> pages;
    private Tile tile;
    
    public AbstractExtendedContext(PageBuilderContext context) {
        this.context = context;
        pages = new HashMap<>();
    }
    
    @Override
    public final void add(String ttname, ExtendedObject object) {
        add(context.view.getPageName(), ttname, object);
    }
    
    @Override
    public final void add(String page, String ttname, ExtendedObject object) {
        TableToolContextEntry entry;
        
        entry = pages.get(page).tabletools.get(ttname);
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
    public final ExtendedObject get(String ttname, int line) {
        return get(context.view.getPageName(), ttname, line);
    }
    
    @Override
    public final ExtendedObject get(String page, String ttname, int line) {
        PageContext pagectx = pages.get(page);
        return pagectx.tabletools.get(ttname).items.get(line);
    }
    
    @Override
    public final boolean isInstantializedTable(String ttname) {
        return isInstantializedTable(context.view.getPageName(), ttname);
    }
    
    @Override
    public final boolean isInstantializedTable(String page, String ttname) {
        PageContext pagectx = pages.get(page);
        return pagectx.tabletools.containsKey(ttname);
    }

    @Override
    public final void move(String pageto, String pagefrom) {
        PageContext pagectxfrom = pages.get(pagefrom);
        PageContext pagectxto = pages.get(pageto);
        
        pagectxto.dataforms.putAll(pagectxfrom.dataforms);
        pagectxto.tabletools.putAll(pagectxfrom.tabletools);
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
    public final void set(Tile tile) {
        this.tile = tile;
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
    public final void set(String ttname, ExtendedObject object, int line) {
        set(context.view.getPageName(), ttname, object, line);
    }
    
    @Override
    public final void set(
            String page, String ttname, ExtendedObject object, int i) {
        TableToolContextEntry entry;

        entry = pages.get(page).tabletools.get(ttname);
        entry.items.put(i, object);
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
    public final void set(String ttname, TableToolItem ttitem) {
        set(context.view.getPageName(), ttname, ttitem);
    }
    
    @Override
    public final void set(String page, String ttname, TableToolItem ttitem) {
        set(page, ttname, ttitem.object, ttitem.position);
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
    
    @Override
    @SuppressWarnings("unchecked")
    public <T> T tileobjectget() {
        return (T)tile.get();
    }
    
}
