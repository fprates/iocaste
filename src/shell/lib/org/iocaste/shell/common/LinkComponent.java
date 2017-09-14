package org.iocaste.shell.common;

import java.util.Map;

public interface LinkComponent extends ControlComponent, Container {

    public abstract boolean isAbsolute();
    
    public abstract String getImage();
    
    public abstract Map<String, LinkEntry> getParametersMap();
    
    public abstract String getText();
    
}
