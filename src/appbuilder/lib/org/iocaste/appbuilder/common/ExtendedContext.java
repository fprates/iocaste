package org.iocaste.appbuilder.common;

import java.util.Collection;

import org.iocaste.appbuilder.common.dataformtool.DataFormContextEntry;
import org.iocaste.appbuilder.common.tabletool.TableToolContextEntry;
import org.iocaste.appbuilder.common.tabletool.TableToolItem;
import org.iocaste.appbuilder.common.tiles.Tile;
import org.iocaste.appbuilder.common.tiles.TilesContextEntry;
import org.iocaste.documents.common.ExtendedObject;

public interface ExtendedContext {

    public abstract void add(String ttname, ExtendedObject object);

    public abstract void add(String page, String ttname, ExtendedObject object);

    public abstract DataFormContextEntry dataformInstance(String dfname);

    public abstract DataFormContextEntry dataformInstance(
            String page, String dfname);
    
    public abstract ExtendedObject dfobjectget(String dfname);
    
    public abstract ExtendedObject dfobjectget(String page, String dfname);

    public abstract ExtendedObject get(String ttname, int line);
    
    public abstract ExtendedObject get(String page, String ttname, int line);
    
    public abstract PageBuilderContext getContext();
    
    public abstract boolean isInstantializedTable(String ttname);
    
    public abstract boolean isInstantializedTable(String page, String ttname);
    
    public abstract void move(String pageto, String pagefrom);
    
    public abstract void pageInstance();
    
    public abstract void pageInstance(String page);

    public abstract void remove(String ttname, ExtendedObject object);
    
    public abstract void remove(
            String pane, String ttname, ExtendedObject object);
    
    public abstract void set(Tile tile);
    
    public abstract void set(String dfname, ExtendedObject object);
    
    public abstract void set(String page, String dfname, ExtendedObject object);

    public abstract void set(String ttname, ExtendedObject[] objects);

    public abstract void set(String page, String ttname,
            ExtendedObject[] objects);

    public abstract void set(String ttname, Collection<ExtendedObject> objects);

    public abstract void set(String page, String ttname,
            Collection<ExtendedObject> objects);
    
    public abstract void set(String ttname, TableToolItem ttitem);
    
    public abstract void set(String page, String ttname, TableToolItem ttitem);

    public abstract void set(String ttname, ExtendedObject object, int line);
    
    public abstract void set(String page, String ttname,
            ExtendedObject object, int line);
    
    public abstract void setDataHandler(ContextDataHandler handler,
            String... tools);
    
    public abstract TableToolContextEntry tableInstance(String ttname);
    
    public abstract TableToolContextEntry tableInstance(
            String page, String ttname);
    
    public abstract TilesContextEntry tilesInstance(String tiles);
    
    public abstract TilesContextEntry tilesInstance(String page, String tiles);

    public abstract <T> T tileobjectget();
}
