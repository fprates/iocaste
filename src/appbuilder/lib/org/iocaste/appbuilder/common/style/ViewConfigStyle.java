package org.iocaste.appbuilder.common.style;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.StyleSheet;

public interface ViewConfigStyle {
    
    public abstract void execute();
    
    public abstract void execute(String media);
    
    public abstract StyleSheet getStyleSheet();

    public abstract void setContext(PageBuilderContext context);
}
