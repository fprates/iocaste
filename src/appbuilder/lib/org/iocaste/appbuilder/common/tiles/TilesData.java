package org.iocaste.appbuilder.common.tiles;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.documents.common.ExtendedObject;

public class TilesData extends AbstractComponentData {
    private ExtendedObject[] objects;
    public TileRenderer tilerenderer;
    
    public TilesData() {
        super(ViewSpecItem.TYPES.TILES);
    }
    
    public final ExtendedObject[] get() {
        return objects;
    }
    
    public final void set(ExtendedObject[] objects) {
        this.objects = objects;
    }
}
