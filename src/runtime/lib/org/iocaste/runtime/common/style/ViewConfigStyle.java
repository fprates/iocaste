package org.iocaste.runtime.common.style;

public interface ViewConfigStyle {
    
    public abstract void execute();
    
    public abstract void execute(String media);
    
    public abstract StyleSheet getStyleSheet();

    public abstract void set(StyleSheet stylesheet);
}
