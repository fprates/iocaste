package org.iocaste.appbuilder.common.style;

import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.StyleSheet;

public abstract class AbstractViewConfigStyle implements ViewConfigStyle {
    private PageBuilderContext context;
    private StyleSheet stylesheet;
    private Map<String, String> style;
    private String media;
    
    public AbstractViewConfigStyle() {
        media = "default";
    }
    
    protected final void clone(String to, String from) {
        style = stylesheet.clone(media, to, from);
    }
    
    protected final void clone(String media, String to, String from) {
        style = stylesheet.clone(media, to, from);
    }
    
    protected final String constant(int code) {
        return stylesheet.getConstants().get(code);
    }
    
    @Override
    public final void execute(String media) {
        this.media = media;
        execute();
    }
    
    protected final void forEachMedia(ViewConfigStyle configstyle) {
        configstyle.setContext(context);
        for (String mediakey : stylesheet.getMedias().keySet())
            configstyle.execute(mediakey);
    }
    
    protected final String get(String property) {
        return style.get(property);
    }
    
    protected final void instance(String name) {
        style = stylesheet.newElement(media, name);
    }
    
    protected final void instance(String media, String name) {
        style = stylesheet.newElement(media, name);
    }
    
    protected final void load(String name) {
        style = stylesheet.get(media, name);
    }
    
    protected final void load(String media, String name) {
        style = stylesheet.get(media, name);
    }
    
    protected final void put(String property, String value, Object... args) {
        if ((args != null) && (args.length > 0))
            value = String.format(value, args);
        style.put(property, value);
    }
    
    @Override
    public void setContext(PageBuilderContext context) {
        this.context = context;
        stylesheet = context.view.styleSheetInstance();
    }

}
