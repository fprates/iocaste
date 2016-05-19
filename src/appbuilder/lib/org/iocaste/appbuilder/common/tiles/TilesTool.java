package org.iocaste.appbuilder.common.tiles;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.AbstractComponentTool;
import org.iocaste.appbuilder.common.BuilderCustomView;
import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.shell.common.CustomView;

public class TilesTool extends AbstractComponentTool {
    private Map<String, Tile> tiles;
    
    /**
     * 
     * @param context
     * @param data
     */
    public TilesTool(ComponentEntry entry) {
        super(entry);
        tiles = new HashMap<>();
    }
    
    public Tile get(String name) {
        return tiles.get(name);
    }
    
    @Override
    public final void load(AbstractComponentData componentdata) {
//        TilesData data = (TilesData)componentdata;
//        
//        for (Tile tile : tiles) {
//            
//        }
    }

    @Override
    public void refresh() {
        
    }

    @Override
    public void run() {
        Tile tile;
        CustomView builder;
        ViewSpecItem itemspec;
        Object[] objects;
        TilesData data = (TilesData)entry.data;
        
        tiles.clear();
        itemspec = data.context.getView().getSpec().get(entry.data.name);
        builder = new BuilderCustomView();
        builder.setView(data.context.view.getPageName());
        builder.setViewSpec(data.spec);
        builder.setViewConfig(data.config);
        builder.setViewInput(data.input);
        
        objects = data.get();
        if (objects == null)
            return;
        for (Object object : objects) {
            if (data.input != null)
                data.input.set(object);
            tile = new Tile(tiles, data.name);
            tile.set(object);
            builder.execute(data.context, itemspec, tile.getName());
        }
    }
}
