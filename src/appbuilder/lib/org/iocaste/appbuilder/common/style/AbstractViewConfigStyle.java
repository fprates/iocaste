package org.iocaste.appbuilder.common.style;

import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.StyleSheet;

public abstract class AbstractViewConfigStyle implements ViewConfigStyle {
    private PageBuilderContext context;
    private Map<String, String> style;
    private String media;
    
    public AbstractViewConfigStyle() {
        media = "default";
    }
    
    protected final void clone(String to, String from) {
        style = context.stylesheet.clone(media, to, from);
    }
    
    protected final void clone(String media, String to, String from) {
        style = context.stylesheet.clone(media, to, from);
    }
    
    protected final String constant(int code) {
        return context.stylesheet.getConstants().get(code);
    }
    
    @Override
    public final void execute(String media) {
        this.media = media;
        execute();
    }
    
    protected final void forEachMedia(ViewConfigStyle configstyle) {
        configstyle.setContext(context);
        for (String mediakey : context.stylesheet.getMedias().keySet())
            configstyle.execute(mediakey);
    }
    
    protected final String get(String property) {
        return style.get(property);
    }
    
    public final StyleSheet getStyleSheet() {
        return context.stylesheet;
    }
    
    protected final void instance(String name) {
        style = context.stylesheet.newElement(media, name);
    }
    
    protected final void instance(String media, String name) {
        style = context.stylesheet.newElement(media, name);
    }
    
    protected final void load(String name) {
        style = context.stylesheet.get(media, name);
    }
    
    protected final void load(String media, String name) {
        style = context.stylesheet.get(media, name);
    }
    
    protected final void put(String property, String value, Object... args) {
        if ((args != null) && (args.length > 0))
            value = String.format(value, args);
        style.put(property, value);
    }
    
    protected final void remove(String property) {
        style.remove(property);
    }
    
    @Override
    public void setContext(PageBuilderContext context) {
        this.context = context;
    }

}
