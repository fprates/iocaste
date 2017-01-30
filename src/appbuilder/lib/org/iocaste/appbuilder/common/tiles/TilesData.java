package org.iocaste.appbuilder.common.tiles;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.AbstractComponentDataItem;
import org.iocaste.appbuilder.common.ViewConfig;
import org.iocaste.appbuilder.common.ViewInput;
import org.iocaste.appbuilder.common.ViewSpec;
import org.iocaste.appbuilder.common.ViewSpecItem;

public class TilesData extends AbstractComponentData {
    public Object[] objects;
    public ViewSpec spec;
    public ViewConfig config;
    public ViewInput input;
    public boolean action;
    
    public TilesData() {
        super(ViewSpecItem.TYPES.TILES);
    }

    @Override
    public <T extends AbstractComponentDataItem> T instance(String name) {
        // TODO Auto-generated method stub
        return null;
    }
}
