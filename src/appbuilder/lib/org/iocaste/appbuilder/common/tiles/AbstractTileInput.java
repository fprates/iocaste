package org.iocaste.appbuilder.common.tiles;

import org.iocaste.appbuilder.common.AbstractViewInput;

public abstract class AbstractTileInput extends AbstractViewInput {
    private Object object;
    
    @SuppressWarnings("unchecked")
    protected final <T> T get() {
        return (T)object;
    }
    
    public final void set(Object object) {
        this.object = object;
    }
}
