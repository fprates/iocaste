package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.cmodelviewer.DataFormContextEntry;
import org.iocaste.appbuilder.common.cmodelviewer.TableToolContextEntry;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
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
        if ((entry.handler != null) && entry.handler.isInitialized())
            entry.handler.add(ttname, object);
        else
            TableToolData.add(this, page, ttname, object);
    }
    
    @Override
    public final DataFormContextEntry dataformInstance(String dfname) {
        return dataformInstance(context.view.getPageName(), dfname);
    }
    
    @Override
    public final DataFormContextEntry dataformInstance(
            String page, String dfname) {
        DataFormContextEntry entry;
        PageContext pagectx;
        
        pagectx = pages.get(page);
        entry = pagectx.dataforms.get(dfname);
        if (entry == null) {
            entry = new DataFormContextEntry();
            pagectx.dataforms.put(dfname, entry);
        }
        return entry;
    }
    
    @Override
    public final ExtendedObject dfobjectget(String dfname) {
        return dfobjectget(context.view.getPageName(), dfname);
    }
    
    @Override
    public final ExtendedObject dfobjectget(String page, String dfname) {
        DataFormContextEntry entry = pages.get(page).dataforms.get(dfname);
        if ((entry.handler != null) && entry.handler.isInitialized())
            return entry.handler.get();
        else
            return entry.object;
    }
    
    @Override
    public final ExtendedObject get(String ttname, int line) {
        return get(context.view.getPageName(), ttname, line);
    }
    
    @Override
    public final ExtendedObject get(String page, String ttname, int line) {
        PageContext pagectx = pages.get(page);
        TableToolItem ttitem = pagectx.tabletools.get(ttname).items.get(line);
        return (ttitem == null)? null : ttitem.object;
    }
    
    @Override
    public final PageBuilderContext getContext() {
        return context;
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
    public final void remove(String ttname, ExtendedObject object) {
        remove(context.view.getPageName(), ttname, object);
    }
    
    @Override
    public final void remove(String page, String ttname, ExtendedObject object)
    {
        TableToolContextEntry entry;
        int index;
        
        entry = pages.get(page).tabletools.get(ttname);
        if ((entry.handler != null) && entry.handler.isInitialized()) {
            entry.handler.remove(ttname, object);
        } else {
            index = -1;
            for (int i : entry.items.keySet()) {
                if (!entry.items.get(i).equals(object))
                    continue;
                index = i;
                break;
            }
            if (index > -1)
                entry.items.remove(index);
        }
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
        if ((entry.handler != null) && entry.handler.isInitialized())
            entry.handler.add(ttname, objects);
        else
            for (ExtendedObject object : objects)
                TableToolData.add(this, page, ttname, object);
    }

    @Override
    public final void set(String ttname, ExtendedObject object, int line) {
        set(context.view.getPageName(), ttname, object, line);
    }
    
    @Override
    public final void set(
            String page, String ttname, ExtendedObject object, int i) {
        TableToolData.set(this, page, ttname, i, object);
    }

    @Override
    public final void set(String dfname, ExtendedObject object) {
        set(context.view.getPageName(), dfname, object);
    }
    
    @Override
    public final void set(String page, String dfname, ExtendedObject object) {
        DataFormContextEntry entry = pages.get(page).dataforms.get(dfname);
        if ((entry.handler != null) && entry.handler.isInitialized())
            entry.handler.set(dfname, object);
        else
            entry.object = object;
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
    public final void setDataHandler(ContextDataHandler handler) {
        setDataHandler(handler, null, null);
    }
    
    @Override
    public final void setDataHandler(ContextDataHandler handler,
            String[] dataforms, String[] tabletools) {
        TableToolContextEntry ttentry;
        DataFormContextEntry dfentry;
        PageContext pagectx = pages.get(context.view.getPageName());
        
        if (tabletools == null)
            tabletools = pagectx.tabletools.keySet().toArray(new String[0]);
        for (String key : tabletools) {
            ttentry = pagectx.tabletools.get(key);
            ttentry.handler = handler;
        }

        if (dataforms == null)
            dataforms = pagectx.dataforms.keySet().toArray(new String[0]);
        for (String key : dataforms) {
            dfentry = pagectx.dataforms.get(key);
            dfentry.handler = handler;
        }
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
            entry = new TableToolContextEntry(ttname);
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
