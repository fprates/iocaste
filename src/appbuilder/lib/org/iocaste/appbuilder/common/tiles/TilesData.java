package org.iocaste.appbuilder.common.tiles;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.ViewConfig;
import org.iocaste.appbuilder.common.ViewSpec;
import org.iocaste.appbuilder.common.ViewSpecItem;

public class TilesData extends AbstractComponentData {
    private Object[] objects;
    public ViewSpec spec;
    public ViewConfig config;
    public AbstractTileInput input;
    public boolean action;
    
    public TilesData() {
        super(ViewSpecItem.TYPES.TILES);
    }
    
    public final Object[] get() {
        return objects;
    }
    
    public final void set(Object[] objects) {
        this.objects = objects;
    }
}
