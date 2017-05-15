package org.iocaste.runtime.common.style;

import java.util.Map;

import org.iocaste.shell.common.StyleSheet;

public abstract class AbstractViewConfigStyle implements ViewConfigStyle {
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
    
    protected final void constant(int code, String value) {
        stylesheet.getConstants().put(code, value);
    }
    
    @Override
    public final void execute(String media) {
        this.media = media;
        execute();
    }
    
    protected final void forEachMedia(ViewConfigStyle configstyle) {
        configstyle.set(stylesheet);
        for (String mediakey : stylesheet.getMedias().keySet())
            configstyle.execute(mediakey);
    }
    
    protected final String get(String property) {
        return style.get(property);
    }
    
    public final String getMediaKey() {
        return media;
    }
    
    public final StyleSheet getStyleSheet() {
        return stylesheet;
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
    
    protected final void media(String name, String rule) {
        stylesheet.instanceMedia(name).setRule(rule);
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
    public void set(StyleSheet stylesheet) {
        this.stylesheet = stylesheet;
    }

}
