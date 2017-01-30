package org.iocaste.appbuilder.common.tiles;

import java.util.ArrayList;

import org.iocaste.appbuilder.common.AbstractContextEntry;
import org.iocaste.appbuilder.common.ViewSpecItem;

public class TilesContextEntry extends AbstractContextEntry {
    public String action;
    public Object value;
    
    public TilesContextEntry() {
        super(ViewSpecItem.TYPES.TILES);
        set(new ArrayList<>());
    }
}
