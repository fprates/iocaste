package org.iocaste.appbuilder.common.tiles;

import org.iocaste.appbuilder.common.AbstractViewInput;

public abstract class AbstractTileInput extends AbstractViewInput {
    private Object object;
    private String action;
    
    @SuppressWarnings("unchecked")
    protected final <T> T get() {
        return (T)object;
    }
    
    protected final String getAction() {
        return action;
    }
    
    public final void set(Object object) {
        this.object = object;
    }
    
    protected final void tileactionset(Object value) {
        action = value.toString();
    }
}
