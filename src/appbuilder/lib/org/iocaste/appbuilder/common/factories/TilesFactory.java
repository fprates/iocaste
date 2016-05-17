package org.iocaste.appbuilder.common.factories;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.appbuilder.common.tiles.TilesData;
import org.iocaste.appbuilder.common.tiles.TilesTool;

public class TilesFactory extends AbstractSpecFactory {

    @Override
    protected AbstractComponentData dataInstance() {
        return new TilesData();
    }

    @Override
    public final void generate(ComponentEntry entry) {
        entry.component = new TilesTool(entry);
    }
}
