package org.iocaste.appbuilder.common.tiles;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.appbuilder.common.ContextEntry;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.documents.common.ExtendedObject;

public class TilesContextEntry implements ContextEntry {
    public List<ExtendedObject> objects;
    
    public TilesContextEntry() {
        objects = new ArrayList<>();
    }
    
    @Override
    public final ViewSpecItem.TYPES getType() {
        return ViewSpecItem.TYPES.TILES;
    }
}
