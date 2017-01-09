package org.iocaste.appbuilder.common;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.dataformtool.DataFormContextEntry;
import org.iocaste.appbuilder.common.tabletool.TableToolContextEntry;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.appbuilder.common.tabletool.TableToolItem;
import org.iocaste.appbuilder.common.tiles.Tile;
import org.iocaste.appbuilder.common.tiles.TilesContextEntry;
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
        ContextDataHandler handler;
        
        entry = pages.get(page).get(ttname);
        handler = entry.getHandler();
        if ((handler != null) && handler.isInitialized())
            handler.add(ttname, object);
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
        entry = pagectx.get(dfname);
        if (entry == null) {
            entry = new DataFormContextEntry();
            pagectx.put(dfname, entry);
        }
        return entry;
    }
    
    @Override
    public final ExtendedObject dfobjectget(String dfname) {
        return dfobjectget(context.view.getPageName(), dfname);
    }
    
    @Override
    public final ExtendedObject dfobjectget(String page, String dfname) {
        DataFormContextEntry entry = pages.get(page).get(dfname);
        ContextDataHandler handler = entry.getHandler();
        if ((handler != null) && handler.isInitialized())
            return handler.get();
        else
            return entry.getObject();
    }
    
    @Override
    public final ExtendedObject get(String ttname, int line) {
        return get(context.view.getPageName(), ttname, line);
    }
    
    @Override
    public final ExtendedObject get(String page, String ttname, int line) {
        PageContext pagectx = pages.get(page);
        TableToolItem ttitem = ((TableToolContextEntry)pagectx.get(ttname)).
                items.get(line);
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
        return pagectx.contains(ttname);
    }

    @Override
    public final void move(String pageto, String pagefrom) {
        PageContext pagectxfrom = pages.get(pagefrom);
        PageContext pagectxto = pages.get(pageto);
        
        pagectxto.putAll(pagectxfrom);
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
        ContextDataHandler handler;
        
        entry = pages.get(page).get(ttname);
        handler = entry.getHandler();
        if ((handler != null) && handler.isInitialized()) {
            handler.remove(ttname, object);
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
        ContextDataHandler handler;

        entry = pages.get(page).get(ttname);
        entry.items.clear();
        if (objects == null)
            return;
        handler = entry.getHandler();
        if ((handler != null) && handler.isInitialized())
            handler.add(ttname, objects);
        else
            for (ExtendedObject object : objects)
                TableToolData.add(this, page, ttname, object);
    }

    @Override
    public final void set(String ttname, Collection<ExtendedObject> objects) {
        set(context.view.getPageName(), ttname, objects);
    }

    @Override
    public final void set(String page, String ttname,
            Collection<ExtendedObject> objects) {
        TableToolContextEntry entry;
        ContextDataHandler handler;

        entry = pages.get(page).get(ttname);
        entry.items.clear();
        if (objects == null)
            return;
        handler = entry.getHandler();
        if ((handler != null) && handler.isInitialized())
            handler.add(ttname, objects);
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
        DataFormContextEntry entry = pages.get(page).get(dfname);
        ContextDataHandler handler = entry.getHandler();
        if ((handler != null) && handler.isInitialized())
            handler.set(dfname, object);
        else
            entry.set(object);
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
    public final void setDataHandler(ContextDataHandler handler,
            String... tools) {
        PageContext pagectx = pages.get(context.view.getPageName());

        if (tools == null)
            for (String key : pagectx.getTools().keySet())
                pagectx.get(key).set(handler);
        else
            for (String key : tools)
                pagectx.get(key).set(handler);
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
        entry = pagectx.get(ttname);
        if (entry == null) {
            entry = new TableToolContextEntry(ttname);
            pagectx.put(ttname, entry);
        }
        return entry;
    }
    
    @Override
    public final TilesContextEntry tilesInstance(String tiles) {
        return tilesInstance(context.view.getPageName(), tiles);
    }
    
    @Override
    public final TilesContextEntry tilesInstance(String page, String tiles) {
        TilesContextEntry entry;
        PageContext pagectx;
        
        pagectx = pages.get(page);
        entry = pagectx.get(tiles);
        if (entry == null) {
            entry = new TilesContextEntry();
            pagectx.put(tiles, entry);
        }
        return entry;
    }
    
    public final void tilesadd(String tiles, ExtendedObject object) {
        tilesadd(context.view.getPageName(), tiles, object);
    }
    
    public final void tilesadd(String page, String tiles, ExtendedObject object)
    {
        TilesContextEntry entry;
        
        entry = pages.get(page).get(tiles);
        entry.add(object);
    }
    
    public final void tilesclear(String tiles) {
        tilesclear(context.view.getPageName());
    }
    
    public final void tilesclear(String page, String tiles) {
        pages.get(page).get(tiles).clear();
    }
    
    public final void tilesset(String tiles, ExtendedObject[] objects) {
        tilesset(context.view.getPageName(), tiles, objects);
    }
    
    public final void tilesset(String page, String tiles,
            ExtendedObject[] objects) {
        TilesContextEntry entry;
        
        entry = pages.get(page).get(tiles);
        entry.clear();
        for (ExtendedObject object : objects)
            entry.add(object);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public <T> T tileobjectget() {
        return (T)tile.get();
    }
    
}
