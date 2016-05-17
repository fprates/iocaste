package org.iocaste.appbuilder.common.tiles;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.documents.common.ExtendedObject;

public abstract class AbstractTileInput extends AbstractViewInput {
    private ExtendedObject object;
    
    protected final ExtendedObject get() {
        return object;
    }
    
    public final void set(ExtendedObject object) {
        this.object = object;
    }
}
