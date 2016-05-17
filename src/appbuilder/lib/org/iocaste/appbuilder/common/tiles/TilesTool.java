package org.iocaste.appbuilder.common.tiles;

import java.util.List;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.AbstractComponentTool;
import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.StandardContainer;

public class TilesTool extends AbstractComponentTool {
    List<Tile> tiles;
    
    /**
     * 
     * @param context
     * @param data
     */
    public TilesTool(ComponentEntry entry) {
        super(entry);
    }
    
    @Override
    public final void load(AbstractComponentData componentdata) {
        TilesData data = (TilesData)componentdata;
        
        for (Tile tile : tiles) {
            
        }
    }

    @Override
    public void refresh() {
        
    }

    @Override
    public void run() {
        Container tilecnt;
        TilesData data = (TilesData)entry.data;
        Container container = data.context.view.getElement(data.name);
        int i = 0;
        
        for (ExtendedObject object : data.get()) {
            tilecnt = new StandardContainer(container, Integer.toString(i++));
            data.tilerenderer.run(tilecnt, object);
        }
    }
}
