package org.iocaste.appbuilder.common.tiles;

import java.util.List;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.AbstractComponentTool;
import org.iocaste.appbuilder.common.BuilderCustomView;
import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.CustomView;
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
        CustomView builder;
        String name;
        ViewSpecItem itemspec;
        int i;
        TilesData data = (TilesData)entry.data;
        
        itemspec = data.context.getView().getSpec().get(entry.data.name);
        if (itemspec == null)
            return;
        
        i = 0;
        builder = new BuilderCustomView();
        builder.setView(data.context.view.getPageName());
        builder.setViewSpec(data.spec);
        builder.setViewConfig(data.config);
        
        for (ExtendedObject object : data.get()) {
            name = new StringBuilder(data.name).append("_").append(i++).
                    toString();
            builder.execute(data.context, itemspec, name);
        }
    }
}
